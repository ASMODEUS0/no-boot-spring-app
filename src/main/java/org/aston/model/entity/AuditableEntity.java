package org.aston.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import org.aston.model.entity.base.EntityBase;

import java.io.Serializable;
import java.time.Instant;
@MappedSuperclass
@Getter
public abstract class AuditableEntity <T extends Serializable> extends EntityBase<T> {

    {
        createdAt = Instant.now();
    }

    @Column(name = "created_at")
    @Builder.Default
    private Instant createdAt;

}
