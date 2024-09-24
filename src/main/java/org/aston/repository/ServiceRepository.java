package org.aston.repository;

import org.aston.model.entity.Service;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

@Component
public class ServiceRepository extends RepositoryBase<Long, Service> {

    public ServiceRepository(SessionFactory sessionFactory) {
        super(Service.class, sessionFactory);
    }
}
