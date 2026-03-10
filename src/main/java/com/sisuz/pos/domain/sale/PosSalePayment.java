package com.sisuz.pos.domain.sale;

import com.sisuz.pos.common.persistence.AuditingEntity;
import com.sisuz.pos.domain.config.PosPaymentMethod;
import com.sisuz.pos.domain.session.PosSession;
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
@Table(name = "pos_sale_payment")
public class PosSalePayment extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private PosSale sale;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = false)
    private PosSession session;

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

    @Size(max = 80)
    @Column(name = "reference", length = 80)
    private String reference;

    @NotNull
    @Column(name = "paid_at", nullable = false)
    private Instant paidAt;

    @Size(max = 80)
    @NotNull
    @Column(name = "received_by", nullable = false, length = 80)
    private String receivedBy;


}