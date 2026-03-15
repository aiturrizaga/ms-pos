package com.sisuz.pos.domain.sale.repository;

import com.sisuz.pos.domain.sale.entity.PosSalePayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosSalePaymentRepository extends JpaRepository<PosSalePayment, Long> {

    List<PosSalePayment> findAllBySaleId(Long saleId);
}
