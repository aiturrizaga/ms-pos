package com.sisuz.pos.domain.config.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record PosConfigUpdateRequest(
        Long storeId,

        @NotNull
        Integer priority,

        @NotNull
        Long inventoryLocationId,

        @NotNull
        Boolean enforceLotSelection,

        @NotNull
        Boolean enforceFefo,

        @NotNull
        Boolean blockExpiredSales,

        Integer warnExpiryDays,

        @NotNull
        Boolean allowNegativeStock,

        @NotNull
        Boolean requireOpeningCash,

        @NotNull
        Boolean requireCloseCount,

        @NotNull
        Boolean requireDenominationCount,

        @NotNull
        Boolean allowCashInOut,

        @NotNull
        Boolean allowCashDrop,

        @NotNull
        Integer maxOpenSessionsPerTerminal,

        @NotNull
        Boolean allowSalesWithoutCustomer,

        Long defaultCustomerId,

        @NotNull
        Boolean allowSplitPayments,

        @NotNull
        Boolean requireReferenceForNonCash,

        @NotNull
        Boolean voidRequiresReason,

        @NotNull
        Boolean refundRequiresReason,

        @NotNull
        Boolean voidRequiresSupervisor,

        @NotNull
        Boolean refundRequiresSupervisor,

        @NotNull
        Boolean requireDocumentOnPayment,

        @NotBlank
        @Size(max = 20)
        String defaultDocumentType,

        @NotNull
        Boolean allowChangeDocumentTypeBeforeIssue,

        @Size(max = 10)
        String receiptSeries,

        @Size(max = 10)
        String invoiceSeries,

        @Size(max = 10)
        String creditNoteSeries,

        @Size(max = 10)
        String debitNoteSeries,

        @NotNull
        Boolean printAfterIssue,

        @NotNull
        Boolean emailAfterIssue,

        @NotNull
        Integer settingsVersion,

        Map<String, Object> settings
) {
}
