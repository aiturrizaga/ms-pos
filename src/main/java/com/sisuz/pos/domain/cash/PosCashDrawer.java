package com.sisuz.pos.domain.cash;

import com.sisuz.pos.common.persistence.AuditingEntity;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "pos_cash_drawer")
public class PosCashDrawer extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "terminal_id", nullable = false)
    private PosTerminal terminal;

    @Size(max = 30)
    @NotNull
    @Column(name = "code", nullable = false, length = 30)
    private String code;

    @Size(max = 80)
    @NotNull
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private boolean active;


}