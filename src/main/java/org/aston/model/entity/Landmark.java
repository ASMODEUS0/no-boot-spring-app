package org.aston.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true, of = {"name", "description"})
@ToString(exclude = {"services", "locality"} )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Landmark extends AuditableEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    private LandmarkType type;

    @ManyToOne()
    @JoinColumn(name = "locality_id")
    private Locality locality;

    @Builder.Default
    @ManyToMany
    @JoinTable(
            name = "landmark_service",
            joinColumns = @JoinColumn(name = "landmark_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();





}
