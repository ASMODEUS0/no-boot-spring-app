package org.aston.mapper;

import org.aston.dto.read.LocalityReadDto;
import org.aston.model.entity.Locality;
import org.springframework.stereotype.Component;

@Component
public class LocalityReadMapper implements Mapper<Locality, LocalityReadDto> {

    @Override
    public LocalityReadDto mapFrom(Locality object) {
        return new LocalityReadDto(
                object.getId(),
                object.getName(),
                object.getPopulation(),
                object.getMetroAvailability()
        );
    }
}
