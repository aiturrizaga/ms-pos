package com.sisuz.pos.domain.sale.repository;

import com.sisuz.pos.domain.sale.entity.PosSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PosSaleRepository extends JpaRepository<PosSale, Long>, JpaSpecificationExecutor<PosSale> {
}
