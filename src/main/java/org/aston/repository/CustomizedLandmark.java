package org.aston.repository;

import org.aston.model.entity.Landmark;
import org.aston.request.LandmarkGetRequest;

import java.util.List;

public interface CustomizedLandmark {
    List<Landmark> findBy(LandmarkGetRequest request);

}
