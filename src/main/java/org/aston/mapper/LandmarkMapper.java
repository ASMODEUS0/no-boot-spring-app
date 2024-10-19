package org.aston.mapper;

import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.model.entity.Landmark;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = LocalityMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LandmarkMapper {


    Landmark mapToEntity(LandmarkCreateDto landmarkCreateDto);

    void updateMap(@MappingTarget Landmark landmark, LandmarkUpdateDto landmarkUpdateDto);

    @Mapping(source = "createdAt", target = "cteatedAt")
    LandmarkReadDto mapToReadDto(Landmark landmark);


}
