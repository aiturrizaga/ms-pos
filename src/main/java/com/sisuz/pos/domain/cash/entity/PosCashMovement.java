package com.sisuz.pos.domain.cash.entity;

import com.sisuz.pos.common.persistence.AuditingEntity;
import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import com.sisuz.pos.domain.session.entity.PosSession;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "pos_cash_movement")
public class PosCashMovement extends AuditingEntity {
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
    @JoinColumn(name = "terminal_id", nullable = false)
    private PosTerminal terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawer_id")
    private PosCashDrawer drawer;

    @Size(max = 30)
    @NotNull
    @Column(name = "movement_type", nullable = false, length = 30)
    private String movementType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PosPaymentMethod paymentMethod;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Size(max = 3)
    @NotNull
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @NotNull
    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Size(max = 30)
    @Column(name = "reason_code", length = 30)
    private String reasonCode;

    @Size(max = 255)
    @Column(name = "note")
    private String note;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_voided", nullable = false)
    private Boolean isVoided;

    @Column(name = "voided_at")
    private Instant voidedAt;

    @Size(max = 80)
    @Column(name = "voided_by", length = 80)
    private String voidedBy;


}