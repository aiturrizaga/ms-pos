package com.sisuz.pos.common.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdBy", "createdDate", "modifiedBy", "modifiedDate" }, allowGetters = true)
public abstract class AuditingEntity {

    @JsonIgnore
    @NotNull
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate = Instant.now();

    @JsonIgnore
    @Size(max = 50)
    @NotNull
    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 80, updatable = false)
    private String createdBy = "system";

    @JsonIgnore
    @LastModifiedDate
    @Column(name = "modified_date")
    private Instant modifiedDate;

    @JsonIgnore
    @Size(max = 50)
    @LastModifiedBy
    @Column(name = "modified_by", length = 80)
    private String modifiedBy;
}
