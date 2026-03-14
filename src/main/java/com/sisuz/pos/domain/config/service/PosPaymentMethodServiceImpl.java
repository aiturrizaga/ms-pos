package com.sisuz.pos.domain.config.service;

import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodFilter;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodResponse;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodUpdateRequest;
import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import com.sisuz.pos.domain.config.mapper.PosPaymentMethodMapper;
import com.sisuz.pos.domain.config.repository.PosPaymentMethodRepository;
import com.sisuz.pos.domain.config.repository.spec.PosPaymentMethodSpecFilter;
import com.sisuz.pos.domain.config.repository.spec.PosPaymentMethodSpecs;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PosPaymentMethodServiceImpl implements PosPaymentMethodService {

    private final PosPaymentMethodRepository paymentMethodRepository;
    private final PosPaymentMethodMapper paymentMethodMapper;

    @Override
    public PosPaymentMethodResponse create(PosPaymentMethodCreateRequest request) {
        PosPaymentMethod entity = paymentMethodMapper.toEntity(request);
        entity.setActive(true);

        return paymentMethodMapper.toResponse(paymentMethodRepository.save(entity));
    }

    @Override
    public PosPaymentMethodResponse update(Long id, PosPaymentMethodUpdateRequest request) {
        PosPaymentMethod entity = paymentMethodRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("Payment method", id));

        paymentMethodMapper.update(request, entity);

        return paymentMethodMapper.toResponse(paymentMethodRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public PosPaymentMethodResponse getById(Long id) {
        PosPaymentMethod entity = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        return paymentMethodMapper.toResponse(entity);
    }

    @Override
    public void activate(Long id) {
        PosPaymentMethod entity = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        entity.setActive(true);
        paymentMethodRepository.save(entity);
    }

    @Override
    public void deactivate(Long id) {
        PosPaymentMethod entity = paymentMethodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment method not found"));

        entity.setActive(false);
        paymentMethodRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PosPaymentMethodResponse> getAll(PosPaymentMethodFilter filter, Pageable pageable) {
        PosPaymentMethodSpecFilter specFilter = PosPaymentMethodSpecFilter.from(filter);
        Specification<PosPaymentMethod> spec = PosPaymentMethodSpecs.withFilters(specFilter);

        return paymentMethodRepository.findAll(spec, pageable)
                .map(paymentMethodMapper::toResponse);
    }
}
