package com.sisuz.pos.domain.config.service;

import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodFilter;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodResponse;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PosPaymentMethodService {
    PosPaymentMethodResponse create(PosPaymentMethodCreateRequest request);

    PosPaymentMethodResponse update(Long id, PosPaymentMethodUpdateRequest request);

    PosPaymentMethodResponse getById(Long id);

    void activate(Long id);

    void deactivate(Long id);

    Page<PosPaymentMethodResponse> getAll(PosPaymentMethodFilter filter, Pageable pageable);
}
