package org.aston.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.mapper.LocalityMapper;
import org.aston.model.entity.Landmark;
import org.aston.model.entity.Locality;
import org.aston.repository.LandmarkRepository;
import org.aston.repository.LocalityRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocalityService {

    private final LocalityRepository localityRepository;
    private final LandmarkRepository landmarkRepository;
    private final LocalityMapper localityMapper;

    @Transactional
    public void add(LocalityCreateDto localityCreateDto) {
        List<Landmark> landmarks = localityCreateDto.landmarksId().stream()
                .map(landmarkRepository::findById)
                .map(mayBeService -> mayBeService.orElseThrow(() ->
                        new EntityNotFoundException("One entity of type: Locality with ids: "
                                                    + localityCreateDto.landmarksId() + " don't present")))
                .toList();

        Locality locality = localityMapper.mapToEntity(localityCreateDto);
        locality.setLandmarks(landmarks);
        localityRepository.save(locality);
    }

    @Transactional
    public void update(LocalityUpdateDto localityUpdateDto) {
        Optional<Locality> mayBeLocality = localityRepository.findById(localityUpdateDto.id());
        Locality locality = mayBeLocality.map(l -> localityMapper.mapToEntity(localityUpdateDto))
                .orElseThrow(() -> new EntityNotFoundException("Entity of type: Locality with id: " + localityUpdateDto.id() + " don't present"));
        localityRepository.update(locality);
    }

}
