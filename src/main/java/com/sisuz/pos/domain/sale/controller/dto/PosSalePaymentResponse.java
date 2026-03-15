package com.sisuz.pos.domain.sale.controller.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PosSalePaymentResponse(
        Long id,
        Long paymentMethodId,
        String paymentMethodCode,
        String paymentMethodName,
        BigDecimal amount,
        String currencyCode,
        String reference,
        Instant paidAt,
        String receivedBy
) {
}
