package org.aston.repository;

import org.aston.model.entity.Landmark;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LandmarkRepositoryJpa extends CrudRepository<Landmark, Long>, CustomizedLandmark {
}
