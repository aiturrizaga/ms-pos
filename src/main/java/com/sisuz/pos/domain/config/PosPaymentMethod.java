package com.sisuz.pos.domain.config;

import com.sisuz.pos.common.persistence.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "pos_payment_method")
public class PosPaymentMethod extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 30)
    @NotNull
    @Column(name = "code", nullable = false, length = 30)
    private String code;

    @Size(max = 60)
    @NotNull
    @Column(name = "name", nullable = false, length = 60)
    private String name;

    @Size(max = 30)
    @NotNull
    @Column(name = "method_type", nullable = false, length = 30)
    private String methodType;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "requires_reference", nullable = false)
    private Boolean requiresReference;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private boolean active;


}