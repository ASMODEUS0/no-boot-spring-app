package org.aston.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.mapper.LandmarkMapper;
import org.aston.model.entity.Landmark;
import org.aston.model.entity.Service;
import org.aston.repository.LandmarkRepositoryJpa;
import org.aston.repository.ServiceRepositoryJpa;
import org.aston.request.LandmarkGetRequest;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LandmarkService {


    private final ServiceRepositoryJpa serviceRepository;
    private final LandmarkMapper landmarkMapper;
    private final LandmarkRepositoryJpa landmarkRepositoryJpa;


    @Transactional
    public List<LandmarkReadDto> getAll(LandmarkGetRequest request) {
        return landmarkRepositoryJpa.findBy(request)
                .stream()
                .map(landmarkMapper::mapToReadDto)
                .toList();
    }

    @Transactional
    public void update(LandmarkUpdateDto landmarkUpdateDto) {
        Objects.requireNonNull(landmarkUpdateDto);
        Optional<Landmark> mayBeLandmark = landmarkRepositoryJpa.findById(landmarkUpdateDto.getId());
        Landmark landmark = mayBeLandmark.map(l -> landmarkMapper.mapToEntity(landmarkUpdateDto))
                .orElseThrow(() -> new EntityNotFoundException("Entity of type: Locality with id: " + landmarkUpdateDto.getId() + " don't present"));
        landmarkRepositoryJpa.save(landmark);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Landmark> mayBeLandmark = landmarkRepositoryJpa.findById(id);
        Landmark landmark = mayBeLandmark.orElseThrow(() -> new EntityNotFoundException("Entity of type: Landmark with id: " + id + " don't present"));
        landmarkRepositoryJpa.delete(landmark);
    }

    @Transactional
    public void save(LandmarkCreateDto landmarkCreateDto) {
        Objects.requireNonNull(landmarkCreateDto);

        List<Service> services = landmarkCreateDto.servicesId().stream()
                .map(serviceRepository::findById)
                .map(mayBeService -> mayBeService.orElseThrow(() ->
                        new EntityNotFoundException("One entity of type: Locality with ids: "
                                                    + landmarkCreateDto.servicesId() + " don't present")))
                .toList();

        Landmark landmark = landmarkMapper.mapToEntity(landmarkCreateDto);
        landmark.setServices(services);
        landmarkRepositoryJpa.save(landmark);
    }


}
