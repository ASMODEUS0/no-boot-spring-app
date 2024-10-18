package org.aston.repository;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aston.model.entity.base.EntityBase;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class RepositoryBase<K extends Serializable, E extends EntityBase<K>> implements Repository<K, E> {

    protected final Class<E> clazz;
    @Getter
    protected final SessionFactory sessionFactory;


    @Override
    public void save(E entity) {
        sessionFactory.getCurrentSession().persist(entity);
    }

    @Override
    public void delete(K id) {
        EntityManager session = sessionFactory.getCurrentSession();
        session.remove(session.find(clazz, id));
        session.flush();
    }

    @Override
    public void update(E entity) {
        sessionFactory.getCurrentSession().merge(entity);
    }

    @Override
    public Optional<E> findById(K id, Map<String, Object> properties) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().find(clazz, id, properties));
    }



}