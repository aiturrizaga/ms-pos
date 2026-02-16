package com.sisuz.pos.domain;

import com.sisuz.pos.common.persistence.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pos_session_payment_count")
public class PosSessionPaymentCount extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private PosSession session;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PosPaymentMethod paymentMethod;

    @NotNull
    @Column(name = "expected_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal expectedAmount;

    @NotNull
    @Column(name = "counted_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal countedAmount;

    @NotNull
    @Column(name = "diff_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal diffAmount;

    @NotNull
    @Column(name = "counted_at", nullable = false)
    private Instant countedAt;

    @Size(max = 80)
    @NotNull
    @Column(name = "counted_by", nullable = false, length = 80)
    private String countedBy;

    @Size(max = 255)
    @Column(name = "note")
    private String note;


}