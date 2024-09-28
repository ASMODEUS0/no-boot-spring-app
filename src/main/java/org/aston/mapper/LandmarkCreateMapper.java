package org.aston.mapper;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.model.entity.Landmark;
import org.aston.repository.ServiceRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LandmarkCreateMapper implements Mapper<LandmarkCreateDto, Landmark>{

    private final ServiceRepository serviceRepository;

    @Override
    public Landmark mapFrom(LandmarkCreateDto object) {
        return Landmark.builder()
                .type(object.type())
                .name(object.name())
                .description(object.description())
                .services(object.servicesId().stream()
                        .map(serviceRepository::findById)
                        .map(mayBeService-> mayBeService.orElseThrow(()->
                                new EntityNotFoundException("An attempt to create a landmark with a " +
                                                            "non-existent entity of type Service")))
                        .toList())
                .build();
    }
}

