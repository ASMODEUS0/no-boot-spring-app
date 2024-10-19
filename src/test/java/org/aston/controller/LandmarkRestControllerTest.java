package org.aston.controller;

import configuration.WebIntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aston.dto.create.LandmarkCreateDto;
import org.aston.dto.update.LandmarkUpdateDto;
import org.aston.model.entity.Landmark;
import org.aston.model.entity.LandmarkType;
import org.aston.request.LandmarkGetRequest;
import org.aston.request.Order;
import org.aston.request.Sort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static util.TestUtil.objectToJson;

class LandmarkRestControllerTest extends WebIntegrationTest {

    private final String NAME = "NAME";
    private final String DESCRIPTION = "DESCRIPTION";
    private final LandmarkType LANDMARK_TYPE = LandmarkType.MUSEUM;
    private final String BASE_PATH = "/landmarks";

    @PersistenceContext
    private  EntityManager entityManager;

    private Landmark landmark;

    @Autowired
    private  WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void configure() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .build();

        landmark = Landmark.builder()
                .name(NAME)
                .description(DESCRIPTION)
                .type(LANDMARK_TYPE)
                .build();
    }


    @Test
    @Transactional
    void addLandmark() throws Exception {
        LandmarkCreateDto landmarkDto = new LandmarkCreateDto(NAME, DESCRIPTION, LANDMARK_TYPE, List.of());

        mockMvc.perform(post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(landmarkDto)))

                .andExpect(jsonPath("$.name").value(NAME))
                .andExpect(jsonPath("$.type").value(LANDMARK_TYPE.toString()))
                .andExpect(jsonPath("$.description").value(DESCRIPTION))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    void getLandmarks() throws Exception {
        entityManager.persist(landmark);
        LandmarkGetRequest landmarkRequest = new LandmarkGetRequest(new Sort(Order.ASC, "name"), List.of(), List.of());

        mockMvc.perform(post(BASE_PATH + "/getAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(landmarkRequest)))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(landmark.getId()));

    }

    @Test
    @Transactional
    void updateLandmark() throws Exception {

        entityManager.persist(landmark);
        String UPDATED_DESCRIPTION = DESCRIPTION + "*_*";
        LandmarkUpdateDto landmarkUpdate = new LandmarkUpdateDto(landmark.getId(), UPDATED_DESCRIPTION);

        mockMvc.perform(put(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(landmarkUpdate)))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(landmark.getId()))
                .andExpect(jsonPath("$.description").value(UPDATED_DESCRIPTION))
        ;
    }

    @Test
    @Transactional
    void deleteLandmark() throws Exception {

        entityManager.persist(landmark);

        mockMvc.perform(delete(BASE_PATH)
                        .param("id", String.valueOf(landmark.getId())))
                .andExpect(status().isOk());

        Landmark mayBeLandmark =  entityManager.find(Landmark.class, landmark.getId());

        assertNull(mayBeLandmark);
    }


}