package org.aston.mapper;

import lombok.AllArgsConstructor;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.model.entity.Landmark;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
@AllArgsConstructor
public class LandmarkReadMapper implements Mapper<Landmark, LandmarkReadDto>{

    private final LocalityReadMapper localityReadMapper;
    @Override
    public LandmarkReadDto mapFrom(Landmark object) {
        return new LandmarkReadDto(
                object.getId(),
                object.getName(),
                object.getCreatedAt(),
                object.getDescription(),
                object.getType(),
                Optional.ofNullable(object.getLocality())
                        .map(localityReadMapper::mapFrom)
                        .orElse(null)
        );
    }
}
