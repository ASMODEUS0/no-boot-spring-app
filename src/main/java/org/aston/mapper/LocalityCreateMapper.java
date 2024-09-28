package org.aston.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.model.entity.Locality;
import org.aston.repository.LandmarkRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalityCreateMapper implements Mapper<LocalityCreateDto, Locality> {

    private final LandmarkRepository landmarkRepository;
    @Override
    public Locality mapFrom(LocalityCreateDto object) {
      return Locality.builder()
                .name(object.name())
                .metroAvailability(object.metroAvailability())
                .population(object.population())
                .landmarks(object.landmarksId().stream()
                        .map(landmarkRepository::findById)
                        .map(mayBeLandmark-> mayBeLandmark.orElseThrow(()-> new EntityNotFoundException("An attempt to create a landmark with a " +
                                                                                                    "non-existent entity of type Service")))
                        .toList())
                .build();

    }
}
