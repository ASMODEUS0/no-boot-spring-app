package org.aston.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.mapper.LocalityCreateMapper;
import org.aston.mapper.LocalityUpdateMapper;
import org.aston.model.entity.Locality;
import org.aston.repository.LocalityRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LocalityService {

    private final LocalityRepository repository;
    private final LocalityCreateMapper localityCreateMapper;
    private final LocalityUpdateMapper localityUpdateMapper;

    @Transactional
    public void add(LocalityCreateDto localityCreateDto) {
        Locality locality = localityCreateMapper.mapFrom(localityCreateDto);
        repository.save(locality);
    }

    @Transactional
    public boolean update(LocalityUpdateDto updateDto){
        Optional<Locality> mayBeLocality = repository.findById(updateDto.id());
        mayBeLocality.ifPresent(locality -> localityUpdateMapper.mapFrom(updateDto, locality));
        return mayBeLocality.isPresent();
    }

}
