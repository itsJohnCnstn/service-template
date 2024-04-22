package com.johncnstn.servicetemplate.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractBaseEntity {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    protected Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected Instant updatedAt;

    @Column(name = "deleted_at")
    protected Instant deletedAt;
}
