package org.aston.request;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class Sort {
    public Order order;
    public String type;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sort sort)) return false;

        if (order != sort.order) return false;
        return Objects.equals(type, sort.type);
    }


}
