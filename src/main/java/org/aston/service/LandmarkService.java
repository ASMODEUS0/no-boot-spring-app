package org.aston.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.mapper.LandmarkCreateMapper;
import org.aston.mapper.LandmarkReadMapper;
import org.aston.mapper.LandmarkUpdateMapper;
import org.aston.model.entity.Landmark;
import org.aston.repository.LandmarkRepository;
import org.aston.request.LandmarkGetRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class LandmarkService {

    private final LandmarkRepository repository;
    private final LandmarkReadMapper landmarkReadMapper;
    private final LandmarkUpdateMapper landmarkUpdateMapper;
    private final LandmarkCreateMapper landmarkCreateMapper;


    @Transactional
    public List<LandmarkReadDto> getAll(LandmarkGetRequest request) {
        return repository.findBy(request)
                .stream()
                .map(landmarkReadMapper::mapFrom)
                .toList();
    }

    @Transactional
    public void update(LandmarkUpdateDto updateDto) {
        Optional<Landmark> mayBeLandmark = repository.findById(updateDto.getId());
        Landmark updatedLandmark = mayBeLandmark.map(landmark -> landmarkUpdateMapper.mapFrom(updateDto, landmark))
                .orElseThrow();
        repository.update(updatedLandmark);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Landmark> mayBeLandmark = repository.findById(id);
        mayBeLandmark.ifPresent(landmark -> repository.delete(landmark.getId()));
        mayBeLandmark.orElseThrow(() -> new EntityNotFoundException("Entity of type: Landmark with id: " + id + " don't present"));
    }

    @Transactional
    public void save(LandmarkCreateDto createDto) {
        Landmark landmark = landmarkCreateMapper.mapFrom(createDto);
        repository.save(landmark);
    }


}
