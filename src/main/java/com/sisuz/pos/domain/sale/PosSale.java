package com.sisuz.pos.domain.sale;

import com.sisuz.pos.common.persistence.AuditingEntity;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
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
@Table(name = "pos_sale")
public class PosSale extends AuditingEntity {
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

    @Size(max = 30)
    @NotNull
    @Column(name = "sale_number", nullable = false, length = 30)
    private String saleNumber;

    @Column(name = "customer_id")
    private Long customerId;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Size(max = 3)
    @NotNull
    @Column(name = "currency_code", nullable = false, length = 3)
    private String currencyCode;

    @NotNull
    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "discount_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal discountTotal;

    @NotNull
    @Column(name = "tax_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxTotal;

    @NotNull
    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Column(name = "paid_at")
    private Instant paidAt;

    @Size(max = 80)
    @NotNull
    @Column(name = "cashier_id", nullable = false, length = 80)
    private String cashierId;

    @Size(max = 255)
    @Column(name = "note")
    private String note;

    @Column(name = "document_id")
    private Long documentId;


}