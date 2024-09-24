package org.aston.dto.read;

import org.aston.model.entity.LandmarkType;

import java.time.Instant;

public record LandmarkReadDto(
        Long id,
        String name,
        Instant cteatedAt,
        String description,
        LandmarkType type,
        LocalityReadDto locality
) {

}
