package com.sisuz.pos.domain.sale.controller.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record PosSaleDetailResponse(

        Long id,
        Long sessionId,
        Long terminalId,
        String saleNumber,
        Long customerId,
        String status,
        String currencyCode,
        BigDecimal subtotal,
        BigDecimal discountTotal,
        BigDecimal taxTotal,
        BigDecimal total,
        Instant paidAt,
        String cashierId,
        String note,
        String documentType,
        Long documentId,
        List<PosSaleLineResponse> lines,
        List<PosSalePaymentResponse> payments

) {}
