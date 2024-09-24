package org.aston.repository;

import org.aston.model.entity.base.EntityBase;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyMap;

public interface Repository<K extends Serializable, E extends EntityBase<K>> {

    void save(E entity);

    void delete(K id);

    void update(E entity);

    default Optional<E> findById(K id) {
        return findById(id, emptyMap());
    }

    Optional<E> findById(K id, Map<String, Object> properties);

}
