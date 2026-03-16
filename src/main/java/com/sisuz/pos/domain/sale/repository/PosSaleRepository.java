package com.sisuz.pos.domain.sale.repository;

import com.sisuz.pos.domain.sale.entity.PosSale;
import com.sisuz.pos.domain.session.controller.dto.PosSaleStatsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PosSaleRepository extends JpaRepository<PosSale, Long>, JpaSpecificationExecutor<PosSale> {
    @Query("""
            SELECT COALESCE(SUM(s.total), 0) AS totalSale, COUNT(s.id) AS transactionQty
            FROM PosSale s
            WHERE s.session.id = :sessionId
    """)
    PosSaleStatsProjection getSaleStatsBySessionId(@Param("sessionId") Long sessionId);
}
