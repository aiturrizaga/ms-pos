package com.sisuz.pos.domain.session.controller.dto;

import java.math.BigDecimal;

public interface PosSaleStatsProjection {
    BigDecimal getTotalSale();
    Long getTransactionQty();
}
