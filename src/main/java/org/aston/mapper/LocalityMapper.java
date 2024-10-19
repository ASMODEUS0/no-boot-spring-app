package org.aston.mapper;

import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.read.LocalityReadDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.model.entity.Locality;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LocalityMapper {
    LocalityReadDto mapToReadDto(Locality locality);

    Locality mapToEntity(LocalityCreateDto localityCreateDto);

    void updateMap(@MappingTarget Locality locality, LocalityUpdateDto localityUpdateDto);

}
