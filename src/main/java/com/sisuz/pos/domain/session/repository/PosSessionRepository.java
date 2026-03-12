package com.sisuz.pos.domain.session.repository;

import com.sisuz.pos.domain.session.entity.PosSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PosSessionRepository extends JpaRepository<PosSession, Long>,
        JpaSpecificationExecutor<PosSession> {

    Optional<PosSession> findFirstByTerminalIdAndStatus(Long terminalId, String status);

}
