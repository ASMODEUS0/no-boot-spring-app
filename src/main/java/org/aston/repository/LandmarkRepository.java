package org.aston.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.aston.model.entity.Landmark;
//import org.aston.model.entity.Landmark_;
//import org.aston.model.entity.Locality_;
import org.aston.model.entity.Landmark_;
import org.aston.model.entity.Locality_;
import org.aston.request.LandmarkGetRequest;
import org.aston.request.Order;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LandmarkRepository extends RepositoryBase<Long, Landmark> {
    public LandmarkRepository(SessionFactory sessionFactory) {
        super(Landmark.class, sessionFactory);
    }

    public List<Landmark> findBy(LandmarkGetRequest request) {
        List<Predicate> predicates = new ArrayList<>();

        EntityManager entityManager = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Landmark> criteria = cb.createQuery(clazz);

        Root<Landmark> root = criteria.from(clazz);

        if (!request.locality.isEmpty()) {
            request.locality.forEach(localityId -> {
                predicates.add(cb.equal(root.get(Landmark_.LOCALITY).get(Locality_.ID), localityId));
            });
        }
        if(!request.type.isEmpty()){
            request.type.forEach(typeInt->{
                predicates.add(cb.equal(root.get(Landmark_.TYPE), typeInt));
            });
        }

        criteria.select(root).where(predicates.toArray(new Predicate[0]));


        if(request.sort != null && request.sort.type != null){
            if(request.sort.order == Order.ASC){
                criteria.orderBy(cb.asc(root.get(request.sort.type)));
            }else{
                criteria.orderBy(cb.desc(root.get(request.sort.type)));
            }
        }
        return entityManager.createQuery(criteria).getResultList();
    }
}
