package org.aston.mapper;

import org.aston.dto.create.ServiceCreateDto;
import org.aston.model.entity.Service;
import org.springframework.stereotype.Component;

@Component
public class ServiceCreateMapper implements Mapper<ServiceCreateDto, Service> {


    @Override
    public Service mapFrom(ServiceCreateDto object) {
        return null;
    }
}
