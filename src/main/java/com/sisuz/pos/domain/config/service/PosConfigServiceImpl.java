package com.sisuz.pos.domain.config.service;

import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.config.controller.dto.PosConfigCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosConfigResponse;
import com.sisuz.pos.domain.config.controller.dto.PosConfigUpdateRequest;
import com.sisuz.pos.domain.config.entity.PosConfig;
import com.sisuz.pos.domain.config.mapper.PosConfigMapper;
import com.sisuz.pos.domain.config.repository.PosConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PosConfigServiceImpl implements PosConfigService {

    private final PosConfigRepository configRepository;
    private final PosConfigMapper configMapper;

    @Override
    public PosConfigResponse create(PosConfigCreateRequest request) {
        PosConfig entity = configMapper.toEntity(request);
        entity.setActive(true);

        return configMapper.toResponse(configRepository.save(entity));
    }

    @Override
    public PosConfigResponse update(Long id, PosConfigUpdateRequest request) {
        PosConfig entity = configRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("PosConfig", id));

        configMapper.update(request, entity);

        return configMapper.toResponse(configRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PosConfigResponse getByStoreId(Long storeId) {
        PosConfig entity = configRepository.findFirstByStoreId(storeId)
                .orElseThrow(() -> NotFoundException.of("Store", storeId));

        return configMapper.toResponse(entity);
    }
}
