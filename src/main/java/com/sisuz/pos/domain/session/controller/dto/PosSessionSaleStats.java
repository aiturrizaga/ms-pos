package com.sisuz.pos.domain.session.controller.dto;

import java.math.BigDecimal;

public record PosSessionSaleStats(BigDecimal totalSale, Long transactionQty) {
    public static PosSessionSaleStats from(PosSaleStatsProjection p) {
        return new PosSessionSaleStats(p.getTotalSale(), p.getTransactionQty());
    }
}
