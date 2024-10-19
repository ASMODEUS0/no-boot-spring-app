package org.aston.service;

import jakarta.persistence.EntityNotFoundException;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.mapper.LocalityMapper;
import org.aston.repository.LandmarkRepository;
import org.aston.repository.LocalityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith({MockitoExtension.class})
class LocalityServiceUnitTest {

    @Mock
    private LocalityRepository localityRepository;
    @Mock
    private LandmarkRepository landmarkRepository;
    @Mock
    private LocalityMapper localityMapper;

    @InjectMocks
    private LocalityService localityService;


    @Test
    void saveThrowExceptionWhenDtoContainsNotExistingLandmarkIds() {
        Mockito.when(landmarkRepository.findById(1L)).thenReturn(Optional.empty());
        LocalityCreateDto localityCreateDto = new LocalityCreateDto("", 1, false, List.of(1L));
        Assertions.assertThrows(EntityNotFoundException.class, () -> localityService.save(localityCreateDto));
    }

    @Test
    void updateThrownExceptionWhenRepositoryDoNotContainData() {
        Mockito.when(localityRepository.findById(1L)).thenReturn(Optional.empty());

        LocalityUpdateDto localityUpdateDto = new LocalityUpdateDto(1L, 1, true);
        Assertions.assertThrows(EntityNotFoundException.class, () -> localityService.update(localityUpdateDto));
    }

}