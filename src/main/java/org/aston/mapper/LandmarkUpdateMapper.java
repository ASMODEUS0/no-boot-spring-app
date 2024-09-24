package org.aston.mapper;

import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.model.entity.Landmark;
import org.springframework.stereotype.Component;

@Component
public class LandmarkUpdateMapper implements UpdateMapper<LandmarkUpdateDto, Landmark> {

    @Override
    public Landmark mapFrom(LandmarkUpdateDto updateDto, Landmark persistentEntity) {
        persistentEntity.setDescription(updateDto.getDescription());
        return persistentEntity;
    }
}
