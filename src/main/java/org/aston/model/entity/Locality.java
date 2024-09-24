package org.aston.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aston.model.entity.base.EntityBase;

import java.util.ArrayList;
import java.util.List;
@Builder
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"landmarks"})
@NoArgsConstructor
@Entity
public class Locality extends EntityBase<Long> {
    private String name;
    private Integer population;
    private Boolean metroAvailability;

    @Builder.Default
    @OneToMany(mappedBy = "locality")
    private List<Landmark> landmarks = new ArrayList<>();

}
