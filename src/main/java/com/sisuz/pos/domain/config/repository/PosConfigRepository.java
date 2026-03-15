package com.sisuz.pos.domain.config.repository;

import com.sisuz.pos.domain.config.entity.PosConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosConfigRepository extends JpaRepository<PosConfig, Long> {

    Optional<PosConfig> findFirstByStoreId(Long storeId);

}
