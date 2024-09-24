package org.aston.dto.read;

public record LocalityReadDto(Long id,
                              String name,
                              Integer population,
                              Boolean metroAvailability
) {

}
