package com.sisuz.pos.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 120)
    @NotNull
    @Column(name = "trade_name", nullable = false, length = 120)
    private String tradeName;

    @Size(max = 120)
    @NotNull
    @Column(name = "legal_name", nullable = false, length = 120)
    private String legalName;

    @Size(max = 120)
    @NotNull
    @Column(name = "legal_owner", nullable = false, length = 120)
    private String legalOwner;

    @Size(max = 60)
    @Column(name = "document_number", length = 60)
    private String documentNumber;

    @NotNull
    @Column(name = "billing_address_id", nullable = false)
    private Integer billingAddressId;

    @NotNull
    @Column(name = "legal_address_id", nullable = false)
    private Integer legalAddressId;

    @Column(name = "logo_file_id")
    private Long logoFileId;

    @NotNull
    @Column(name = "state", nullable = false)
    private Integer state;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Size(max = 80)
    @NotNull
    @Column(name = "created_by", nullable = false, length = 80)
    private String createdBy;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_date", nullable = false)
    private OffsetDateTime createdDate;

    @Size(max = 80)
    @Column(name = "modified_by", length = 80)
    private String modifiedBy;

    @Column(name = "modified_date")
    private OffsetDateTime modifiedDate;


}