package org.aston.mapper;

import org.aston.dto.update.LocalityUpdateDto;
import org.aston.model.entity.Locality;
import org.springframework.stereotype.Component;

@Component
public class LocalityUpdateMapper implements UpdateMapper<LocalityUpdateDto, Locality>{

    @Override
    public Locality mapFrom(LocalityUpdateDto updateDto,
                            Locality persistentEntity) {
        persistentEntity.setPopulation(updateDto.population());
        persistentEntity.setMetroAvailability(updateDto.metroAvailability());
        return persistentEntity;
    }
}
