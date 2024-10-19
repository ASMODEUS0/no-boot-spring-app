package org.aston.service;

import jakarta.persistence.EntityNotFoundException;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.read.LandmarkReadDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.mapper.LandmarkMapper;
import org.aston.model.entity.LandmarkType;
import org.aston.repository.LandmarkRepository;
import org.aston.repository.ServiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class LandmarkServiceUnitTest {

    @Mock
    private LandmarkMapper landmarkMapper;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private LandmarkRepository landmarkRepository;

    @InjectMocks
    private LandmarkService landmarkService;


    @Test
    void getAllWithNullRequest() {
        Mockito.when(landmarkRepository.findBy(null)).thenReturn(new ArrayList<>());

        List<LandmarkReadDto> mayBeLandmarks = landmarkService.getAll(null);

        Assertions.assertNotNull(mayBeLandmarks);
        Assertions.assertTrue(mayBeLandmarks.isEmpty());
    }

    @Test
    void updateThrownExceptionWhenRepositoryDoNotContainData() {
        Mockito.when(landmarkRepository.findById(1L)).thenReturn(Optional.empty());
        LandmarkUpdateDto landmarkUpdateDto = new LandmarkUpdateDto(1L, "");

        Assertions.assertThrows(EntityNotFoundException.class, () -> landmarkService.update(landmarkUpdateDto));
    }

    @Test
    void deleteReturnFalseWhenRepositoryDoNotContainData() {
        Mockito.when(landmarkRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertFalse(landmarkService.delete(1L));
    }

    @Test
    void saveThrownExceptionWhenGetNullParam() {
        Assertions.assertThrows(NullPointerException.class, () -> landmarkService.save(null));
    }

    @Test
    void saveThrowExceptionWhenDtoContainsNotExistingLocalityIds() {
        Mockito.when(serviceRepository.findById(1L)).thenReturn(Optional.empty());
        LandmarkCreateDto landmarkCreateDto = new LandmarkCreateDto("", "", LandmarkType.MUSEUM, List.of(1L));

        Assertions.assertThrows(EntityNotFoundException.class, () -> landmarkService.save(landmarkCreateDto));

    }


}