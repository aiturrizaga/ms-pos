package com.sisuz.pos.domain.config.service;

import com.sisuz.pos.domain.config.controller.dto.PosConfigCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosConfigResponse;
import com.sisuz.pos.domain.config.controller.dto.PosConfigUpdateRequest;

public interface PosConfigService {

    PosConfigResponse create(PosConfigCreateRequest request);

    PosConfigResponse update(Long id, PosConfigUpdateRequest request);

    PosConfigResponse getByStoreId(Long storeId);

}
