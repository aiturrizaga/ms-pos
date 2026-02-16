package com.sisuz.pos.domain;

import com.sisuz.pos.common.persistence.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "pos_config")
public class PosConfig extends AuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "store_id")
    private Long storeId;

    @Size(max = 80)
    @NotNull
    @Column(name = "name", nullable = false, length = 80)
    private String name;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private boolean active;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "priority", nullable = false)
    private Integer priority;

    @NotNull
    @Column(name = "inventory_location_id", nullable = false)
    private Long inventoryLocationId;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "enforce_lot_selection", nullable = false)
    private Boolean enforceLotSelection;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "enforce_fefo", nullable = false)
    private Boolean enforceFefo;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "block_expired_sales", nullable = false)
    private Boolean blockExpiredSales;

    @Column(name = "warn_expiry_days")
    private Integer warnExpiryDays;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "allow_negative_stock", nullable = false)
    private Boolean allowNegativeStock;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "require_opening_cash", nullable = false)
    private Boolean requireOpeningCash;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "require_close_count", nullable = false)
    private Boolean requireCloseCount;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "require_denomination_count", nullable = false)
    private Boolean requireDenominationCount;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "allow_cash_in_out", nullable = false)
    private Boolean allowCashInOut;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "allow_cash_drop", nullable = false)
    private Boolean allowCashDrop;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "max_open_sessions_per_terminal", nullable = false)
    private Integer maxOpenSessionsPerTerminal;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "allow_sales_without_customer", nullable = false)
    private Boolean allowSalesWithoutCustomer;

    @Column(name = "default_customer_id")
    private Long defaultCustomerId;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "allow_split_payments", nullable = false)
    private Boolean allowSplitPayments;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "require_reference_for_non_cash", nullable = false)
    private Boolean requireReferenceForNonCash;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "void_requires_reason", nullable = false)
    private Boolean voidRequiresReason;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "refund_requires_reason", nullable = false)
    private Boolean refundRequiresReason;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "void_requires_supervisor", nullable = false)
    private Boolean voidRequiresSupervisor;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "refund_requires_supervisor", nullable = false)
    private Boolean refundRequiresSupervisor;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "require_document_on_payment", nullable = false)
    private Boolean requireDocumentOnPayment;

    @Size(max = 20)
    @NotNull
    @Column(name = "default_document_type", nullable = false, length = 20)
    private String defaultDocumentType;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "allow_change_document_type_before_issue", nullable = false)
    private Boolean allowChangeDocumentTypeBeforeIssue;

    @Size(max = 10)
    @Column(name = "receipt_series", length = 10)
    private String receiptSeries;

    @Size(max = 10)
    @Column(name = "invoice_series", length = 10)
    private String invoiceSeries;

    @Size(max = 10)
    @Column(name = "credit_note_series", length = 10)
    private String creditNoteSeries;

    @Size(max = 10)
    @Column(name = "debit_note_series", length = 10)
    private String debitNoteSeries;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "print_after_issue", nullable = false)
    private Boolean printAfterIssue;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "email_after_issue", nullable = false)
    private Boolean emailAfterIssue;

    @NotNull
    @ColumnDefault("1")
    @Column(name = "settings_version", nullable = false)
    private Integer settingsVersion;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "settings")
    private Map<String, Object> settings;


}