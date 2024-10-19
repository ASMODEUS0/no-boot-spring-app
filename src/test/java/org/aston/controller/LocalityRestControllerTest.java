package org.aston.controller;

import configuration.WebIntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aston.dto.create.LocalityCreateDto;
import org.aston.dto.update.LocalityUpdateDto;
import org.aston.model.entity.Locality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import util.TestUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LocalityRestControllerTest extends WebIntegrationTest {

    private Locality locality;
    private final String NAME = "name";
    private final Integer POPULATION = 100;
    private final boolean METRO_AVAILABILITY = true;

    private final String BASE_PATH = "/locality";

    @Autowired
    private WebApplicationContext applicationContext;

    @PersistenceContext
    private EntityManager entityManager;


    private MockMvc mockMvc;

    @BeforeEach
    void configure() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .build();

        locality = Locality.builder()
                .name(NAME)
                .population(POPULATION)
                .metroAvailability(METRO_AVAILABILITY)
                .build();

    }


    @Test
    void addLocality() throws Exception {
        LocalityCreateDto localityCreateDto = new LocalityCreateDto(NAME, POPULATION, METRO_AVAILABILITY, List.of());

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.objectToJson(localityCreateDto)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.metroAvailability").value(METRO_AVAILABILITY))
                .andExpect(jsonPath("$.population").value(POPULATION))
                .andExpect(jsonPath("$.name").value(NAME));
    }


    @Test
    @Transactional
    void updateLocality() throws Exception {
        entityManager.persist(locality);
        Integer UPDATED_POPULATION = POPULATION + 1;
        boolean UPDATED_METRO_AVAILABILITY = !METRO_AVAILABILITY;
        LocalityUpdateDto localityUpdateDto = new LocalityUpdateDto(locality.getId(), UPDATED_POPULATION, UPDATED_METRO_AVAILABILITY);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.objectToJson(localityUpdateDto)))


                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metroAvailability").value(UPDATED_METRO_AVAILABILITY))
                .andExpect(jsonPath("$.population").value(UPDATED_POPULATION))
                .andExpect(jsonPath("$.id").value(locality.getId()));
    }
}