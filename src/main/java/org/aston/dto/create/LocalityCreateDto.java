package org.aston.dto.create;


import java.util.List;

public record LocalityCreateDto(String name,
                                Integer population,
                                Boolean metroAvailability,
                                List<Long> landmarksId) {
}
