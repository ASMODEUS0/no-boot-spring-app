package org.aston.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.*;
import org.aston.model.entity.base.EntityBase;

import java.util.ArrayList;
import java.util.List;
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Service extends EntityBase<Long> {
    private String name;
    private String description;
    @Builder.Default
    @ManyToMany(mappedBy = "services")
    private List<Landmark> landmarks = new ArrayList<>();

}
