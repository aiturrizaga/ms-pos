package com.sisuz.pos.domain.session.entity;

import com.sisuz.pos.common.persistence.AuditingEntity;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import com.sisuz.pos.domain.cash.PosCashDrawer;
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
@Table(name = "pos_session")
public class PosSession extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_id", nullable = false)
    private PosTerminal terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drawer_id")
    private PosCashDrawer drawer;

    @Size(max = 80)
    @NotNull
    @Column(name = "opened_by", nullable = false, length = 80)
    private String openedBy;

    @NotNull
    @Column(name = "opened_at", nullable = false)
    private Instant openedAt;

    @Size(max = 80)
    @Column(name = "closed_by", length = 80)
    private String closedBy;

    @Column(name = "closed_at")
    private Instant closedAt;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Size(max = 255)
    @Column(name = "opening_note")
    private String openingNote;

    @Size(max = 255)
    @Column(name = "closing_note")
    private String closingNote;

    @Column(name = "expected_total_amount", precision = 12, scale = 2)
    private BigDecimal expectedTotalAmount;

    @Column(name = "counted_total_amount", precision = 12, scale = 2)
    private BigDecimal countedTotalAmount;

    @Column(name = "diff_total_amount", precision = 12, scale = 2)
    private BigDecimal diffTotalAmount;


}