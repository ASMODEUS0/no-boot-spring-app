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
import org.aston.repository.LandmarkRepository;
import org.aston.repository.ServiceRepository;
import org.aston.request.LandmarkGetRequest;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@AllArgsConstructor
public class LandmarkService {


    private final ServiceRepository serviceRepository;
    private final LandmarkMapper landmarkMapper;
    private final LandmarkRepository landmarkRepository;


    @Transactional
    public List<LandmarkReadDto> getAll(LandmarkGetRequest request) {
        return landmarkRepository.findBy(request)
                .stream()
                .map(landmarkMapper::mapToReadDto)
                .toList();
    }

    @Transactional
    public LandmarkReadDto update(LandmarkUpdateDto landmarkUpdateDto) {
        return landmarkRepository.findById(landmarkUpdateDto.getId())
                .map(landmark -> {
                    landmarkMapper.updateMap(landmark, landmarkUpdateDto);
                    return landmark;
                })
                .map(landmarkRepository::save)
                .map(landmarkMapper::mapToReadDto)
                .orElseThrow(() -> new EntityNotFoundException("Entity of type: Locality with id: " + landmarkUpdateDto.getId() + " don't present"));

    }

    @Transactional
    public boolean delete(Long id) {
        return landmarkRepository.findById(id).map(landmark -> {
            landmarkRepository.delete(landmark);
            return true;
        }).orElse(false);
    }

    @Transactional
    public LandmarkReadDto save(LandmarkCreateDto landmarkCreateDto) {
        Objects.requireNonNull(landmarkCreateDto);

        List<Service> services = landmarkCreateDto.servicesId().stream()
                .map(serviceRepository::findById)
                .map(mayBeService -> mayBeService.orElseThrow(() ->
                        new EntityNotFoundException("One entity of type: Locality with ids: "
                                                    + landmarkCreateDto.servicesId() + " don't present")))
                .toList();

        Landmark landmark = landmarkMapper.mapToEntity(landmarkCreateDto);
        landmark.setServices(services);
        Landmark savedLandmark = landmarkRepository.save(landmark);
        return landmarkMapper.mapToReadDto(savedLandmark);
    }


}
