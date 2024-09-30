package org.aston.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aston.model.entity.base.EntityBase;

import java.util.ArrayList;
import java.util.List;
@Data
@ToString(exclude = {"landmarks"})
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Locality implements EntityBase<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer population;
    private Boolean metroAvailability;

    @Builder.Default
    @OneToMany(mappedBy = "locality")
    private List<Landmark> landmarks = new ArrayList<>();


}
