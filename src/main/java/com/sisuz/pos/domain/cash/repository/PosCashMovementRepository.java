package com.sisuz.pos.domain.cash.repository;

import com.sisuz.pos.domain.cash.entity.PosCashMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PosCashMovementRepository extends JpaRepository<PosCashMovement, Long>, JpaSpecificationExecutor<PosCashMovement> {
}
