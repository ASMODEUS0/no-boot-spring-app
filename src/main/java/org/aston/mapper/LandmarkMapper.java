package org.aston.mapper;

import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.model.entity.Landmark;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = LocalityMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LandmarkMapper {


    Landmark mapToEntity(LandmarkCreateDto landmarkCreateDto);

    Landmark mapToEntity(LandmarkUpdateDto landmarkUpdateDto);

    LandmarkReadDto mapToReadDto(Landmark landmark);


}
