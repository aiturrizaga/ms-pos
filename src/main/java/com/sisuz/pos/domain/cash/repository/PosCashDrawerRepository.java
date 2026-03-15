package com.sisuz.pos.domain.cash.repository;

import com.sisuz.pos.domain.cash.entity.PosCashDrawer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface PosCashDrawerRepository extends JpaRepository<PosCashDrawer, Long>, JpaSpecificationExecutor<PosCashDrawer> {

    boolean existsByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCaseAndIdNot(String code, Long id);

    Optional<PosCashDrawer> findFirstByTerminalIdAndCodeIgnoreCase(Long terminalId, String code);

    Optional<PosCashDrawer> findFirstByTerminalCompanyId(UUID companyId);
}