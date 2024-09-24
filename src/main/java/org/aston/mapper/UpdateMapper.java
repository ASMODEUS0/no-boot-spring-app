package org.aston.mapper;

import org.aston.dto.update.UpdateDto;

public interface UpdateMapper <F extends UpdateDto, T>{
    T mapFrom(F updateDto, T persistentEntity);
}
