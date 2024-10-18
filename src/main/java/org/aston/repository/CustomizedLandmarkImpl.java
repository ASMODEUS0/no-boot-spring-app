package org.aston.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.aston.model.entity.Landmark;
import org.aston.model.entity.Landmark_;
import org.aston.model.entity.Locality_;
import org.aston.request.LandmarkGetRequest;
import org.aston.request.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Repository
public class CustomizedLandmarkImpl implements CustomizedLandmark {

    private final Class<Landmark> clazz = Landmark.class;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Landmark> findBy(LandmarkGetRequest request) {

        if (request == null) {
            return new ArrayList<>();
        }

        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Landmark> criteria = cb.createQuery(clazz);

        Root<Landmark> root = criteria.from(clazz);


        if (!CollectionUtils.isEmpty(request.locality)) {
            request.locality.forEach(localityId -> predicates.add(
                    cb.equal(root
                            .get(Landmark_.LOCALITY)
                            .get(Locality_.ID), localityId)
            ));
        }

        if (!CollectionUtils.isEmpty(request.type)) {
            request.type.forEach(typeInt -> predicates.add(
                    cb.equal(root.get(Landmark_.TYPE), typeInt)
            ));
        }

        criteria.select(root).where(predicates.toArray(new Predicate[0]));


        if (request.sort != null && request.sort.type != null) {
            if (request.sort.order == Order.ASC) {
                criteria.orderBy(cb.asc(root.get(request.sort.type)));
            } else {
                criteria.orderBy(cb.desc(root.get(request.sort.type)));
            }
        }
        return entityManager.createQuery(criteria).getResultList();
    }


}
