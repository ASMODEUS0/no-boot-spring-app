package org.aston.repository;

import configuration.IntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aston.model.entity.Landmark;
import org.aston.model.entity.LandmarkType;
import org.aston.model.entity.Locality;
import org.aston.request.LandmarkGetRequest;
import org.aston.request.Order;
import org.aston.request.Sort;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
class LandmarkRepositoryTest extends IntegrationTest {

    @Autowired
    private LandmarkRepository landmarkRepository;
    //
    @Autowired
    private TransactionTemplate transactionTemplate;

    private Landmark landmark;

    //PersistenceContext - Jpa контекст, который хранит сохраненные и полученные сущности.
    //Хранит информацию о всех изменениях хранимых сущнотсей (dirty entity). По окончании транзакции - flush() dirty данные в базу данных.
    //EntityManager взаимодействует с PersistenceContext
    //В spring аннотация проксирует объект для динамического определения EntityManager текущей сессии.
    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setUpTest() {
        landmark = Landmark.builder()
                .name("test")
                .build();
    }


    @Test
    void save() {

        landmarkRepository.save(landmark);

        Landmark mayBeLandmark = entityManager.find(Landmark.class, landmark.getId());

        assertNotNull(mayBeLandmark);
        assertEquals(mayBeLandmark.getId(), landmark.getId());
    }

    @Test
    void deleteLandmarkSuccessfully() {

        entityManager.persist(landmark);

        landmarkRepository.delete(landmark.getId());

        Landmark mayBeLandmark = entityManager.find(Landmark.class, landmark.getId());

        assertNull(mayBeLandmark);
    }

    @Test
    void update() {
        String UPDATED_NAME = "updated";

        entityManager.persist(landmark);

        landmark.setName(UPDATED_NAME);

        landmarkRepository.update(landmark);

        Landmark mayBeLandmark = entityManager.find(Landmark.class, landmark.getId());

        assertNotNull(mayBeLandmark);
        assertEquals(mayBeLandmark.getName(), UPDATED_NAME);

    }

    @Test
    void findById() {

        entityManager.persist(landmark);

        Optional<Landmark> mayBeLandmark = landmarkRepository.findById(landmark.getId());

        assertTrue(mayBeLandmark.isPresent());
        assertEquals(mayBeLandmark.get().getId(), landmark.getId());
    }


    @Nested
    class FindByTest {

        private List<Landmark> landmarks;

        private Locality locality;


        @BeforeEach
        void setUp() {
            landmarks = new ArrayList<>();

            locality = Locality.builder().build();
            Locality locality2 = Locality.builder().build();

            landmarks.add(Landmark.builder().name("3").type(LandmarkType.PARK).locality(locality).build());
            landmarks.add(Landmark.builder().name("1").type(LandmarkType.MUSEUM).locality(locality).build());
            landmarks.add(Landmark.builder().name("2").type(LandmarkType.PARK).locality(locality2).build());
            landmarks.add(Landmark.builder().name("4").type(LandmarkType.MUSEUM).locality(locality2).build());
            landmarks.add(Landmark.builder().name("5").type(LandmarkType.MUSEUM).locality(locality).build());

            transactionTemplate.executeWithoutResult(status -> landmarks.forEach(entityManager::persist));
        }


        @Test
        @Transactional
        void testFindByNullRequest() {

            List<Landmark> landmarksCur = landmarkRepository.findBy(null);

            assertNotNull(landmarksCur);
            assertEquals(landmarksCur.size(), 0);
        }

        @Test
        @Transactional
        void testFindByEmptyRequest() {
            LandmarkGetRequest request = new LandmarkGetRequest();

            List<Landmark> landmarksCur = landmarkRepository.findBy(request);

            assertNotNull(landmarksCur);
            assertEquals(landmarksCur.size(), 5);
        }


        @Test
        @Transactional
        void testFindByAscendingSort() {
            LandmarkGetRequest request = new LandmarkGetRequest();
            landmarks.sort(Comparator.comparing(Landmark::getName));
            request.sort = new Sort(Order.ASC, "name");

            List<Landmark> landmarksCur = landmarkRepository.findBy(request);

            assertNotNull(landmarksCur);
            assertEquals(landmarksCur.size(), 5);
            assertEquals(landmarksCur, landmarks);
        }

        @Test
        @Transactional
        void testFindByDescSort() {
            LandmarkGetRequest request = new LandmarkGetRequest();
            landmarks.sort(Comparator.comparing(Landmark::getName).reversed());
            request.sort = new Sort(Order.DESC, "name");

            List<Landmark> landmarksCur = landmarkRepository.findBy(request);

            assertNotNull(landmarksCur);
            assertEquals(landmarksCur.size(), 5);
            assertEquals(landmarksCur, landmarks);
        }


        @Test
        @Transactional
        void testFindByType() {
            LandmarkGetRequest request = new LandmarkGetRequest();
            request.locality = List.of(locality.getId().intValue());

            List<Landmark> landmarksCur = landmarkRepository.findBy(request);

            assertNotNull(landmarksCur);
            assertEquals(landmarksCur.size(), 3);
            assertTrue(landmarksCur.stream()
                    .allMatch(landmarkMayBeMuseum -> landmarkMayBeMuseum.getLocality().equals(locality)));

        }


        @Test
        @Transactional
        void testFindByLocality() {
            LandmarkGetRequest request = new LandmarkGetRequest();
            request.type = List.of(LandmarkType.MUSEUM.ordinal());

            List<Landmark> landmarksCur = landmarkRepository.findBy(request);

            assertNotNull(landmarksCur);
            assertEquals(landmarksCur.size(), 3);
            assertTrue(landmarksCur.stream()
                    .allMatch(landmarkMayBeMuseum -> landmarkMayBeMuseum.getType() == LandmarkType.MUSEUM));

        }
    }

}