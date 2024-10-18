package org.aston.repository;

import org.aston.model.entity.Locality;
import org.springframework.data.repository.CrudRepository;

public interface LocalityRepositoryJpa extends CrudRepository<Locality, Long> {
}
