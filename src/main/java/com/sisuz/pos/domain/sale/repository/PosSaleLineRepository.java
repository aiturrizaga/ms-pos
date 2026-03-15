package com.sisuz.pos.domain.sale.repository;

import com.sisuz.pos.domain.sale.entity.PosSaleLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosSaleLineRepository extends JpaRepository<PosSaleLine, Long> {

    List<PosSaleLine> findAllBySaleId(Long saleId);
}
