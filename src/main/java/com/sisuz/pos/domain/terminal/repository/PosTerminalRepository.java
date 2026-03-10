package com.sisuz.pos.domain.terminal.repository;

import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PosTerminalRepository extends JpaRepository<PosTerminal, Long>, JpaSpecificationExecutor<PosTerminal> {
}
