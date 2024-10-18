package org.aston.repository;

import org.aston.model.entity.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepositoryJpa extends CrudRepository<Service, Long> {
}
