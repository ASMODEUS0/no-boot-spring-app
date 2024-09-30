package org.aston.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aston.model.entity.base.EntityBase;

import java.util.ArrayList;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service implements EntityBase<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Builder.Default
    @ManyToMany(mappedBy = "services")
    private List<Landmark> landmarks = new ArrayList<>();


}
