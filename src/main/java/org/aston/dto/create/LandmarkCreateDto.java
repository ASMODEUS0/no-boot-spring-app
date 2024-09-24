package org.aston.dto.create;

import org.aston.model.entity.LandmarkType;

import java.util.List;

public record LandmarkCreateDto(String name,
                                String description,
                                LandmarkType type,
                                List<Long> servicesId) {
}
