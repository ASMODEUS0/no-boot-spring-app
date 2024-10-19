package org.aston.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.read.LocalityReadDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.mapper.LocalityMapper;
import org.aston.model.entity.Landmark;
import org.aston.model.entity.Locality;
import org.aston.repository.LandmarkRepository;
import org.aston.repository.LocalityRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocalityService {

    private final LocalityRepository localityRepository;
    private final LandmarkRepository landmarkRepository;
    private final LocalityMapper localityMapper;

    @Transactional
    public LocalityReadDto save(LocalityCreateDto localityCreateDto) {
        List<Landmark> landmarks = localityCreateDto.landmarksId().stream()
                .map(landmarkRepository::findById)
                .map(mayBeService -> mayBeService.orElseThrow(() ->
                        new EntityNotFoundException("One entity of type: Locality with ids: "
                                                    + localityCreateDto.landmarksId() + " don't present")))
                .toList();

        Locality locality = localityMapper.mapToEntity(localityCreateDto);
        locality.setLandmarks(landmarks);
        localityRepository.save(locality);
        return localityMapper.mapToReadDto(locality);
    }

    @Transactional
    public LocalityReadDto update(LocalityUpdateDto localityUpdateDto) {
       return  localityRepository.findById(localityUpdateDto.id())
                .map(locality -> {
                    localityMapper.updateMap(locality, localityUpdateDto);
                    return locality;
                })
                .map(localityRepository::save)
                .map(localityMapper::mapToReadDto)
                .orElseThrow(() -> new EntityNotFoundException("Entity of type: Locality with id: " + localityUpdateDto.id() + " don't present"));
    }

}
