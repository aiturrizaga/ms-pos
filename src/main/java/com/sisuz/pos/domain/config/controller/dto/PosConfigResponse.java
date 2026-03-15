package com.sisuz.pos.domain.config.controller.dto;

import java.util.Map;

public record PosConfigResponse(
        Long id,
        Long storeId,
        String name,
        boolean active,
        Integer priority,
        Long inventoryLocationId,
        Boolean enforceLotSelection,
        Boolean enforceFefo,
        Boolean blockExpiredSales,
        Integer warnExpiryDays,
        Boolean allowNegativeStock,
        Boolean requireOpeningCash,
        Boolean requireCloseCount,
        Boolean requireDenominationCount,
        Boolean allowCashInOut,
        Boolean allowCashDrop,
        Integer maxOpenSessionsPerTerminal,
        Boolean allowSalesWithoutCustomer,
        Long defaultCustomerId,
        Boolean allowSplitPayments,
        Boolean requireReferenceForNonCash,
        Boolean voidRequiresReason,
        Boolean refundRequiresReason,
        Boolean voidRequiresSupervisor,
        Boolean refundRequiresSupervisor,
        Boolean requireDocumentOnPayment,
        String defaultDocumentType,
        Boolean allowChangeDocumentTypeBeforeIssue,
        String receiptSeries,
        String invoiceSeries,
        String creditNoteSeries,
        String debitNoteSeries,
        Boolean printAfterIssue,
        Boolean emailAfterIssue,
        Integer settingsVersion,
        Map<String, Object> settings
) {
}
