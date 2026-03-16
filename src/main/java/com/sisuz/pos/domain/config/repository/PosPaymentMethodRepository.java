package com.sisuz.pos.domain.config.repository;

import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PosPaymentMethodRepository extends JpaRepository<PosPaymentMethod, Long>, JpaSpecificationExecutor<PosPaymentMethod> {
}
