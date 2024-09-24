package org.aston.repository;

import org.aston.model.entity.Locality;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class LocalityRepository extends RepositoryBase<Long, Locality>{
    public LocalityRepository(SessionFactory sessionFactory) {
        super(Locality.class, sessionFactory);
    }
}
