package org.aston.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class LandmarkGetRequest {
    public Sort sort;
    public List<Integer> type;
    public List<Integer> locality;
}
