package org.aston.dto.update;



public record LocalityUpdateDto(Long id, Integer population, Boolean metroAvailability) implements UpdateDto {
}
