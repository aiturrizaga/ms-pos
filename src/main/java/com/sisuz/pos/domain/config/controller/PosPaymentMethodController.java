package com.sisuz.pos.domain.config.controller;

import com.sisuz.pos.common.api.ApiResponse;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodFilter;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodResponse;
import com.sisuz.pos.domain.config.controller.dto.PosPaymentMethodUpdateRequest;
import com.sisuz.pos.domain.config.service.PosPaymentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/payment-methods")
@RequiredArgsConstructor
public class PosPaymentMethodController {

    private final PosPaymentMethodService paymentMethodService;

    @PostMapping
    public ApiResponse<PosPaymentMethodResponse> create(
            @RequestBody @Valid PosPaymentMethodCreateRequest request
    ) {
        return ApiResponse.success(paymentMethodService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PosPaymentMethodResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid PosPaymentMethodUpdateRequest request
    ) {
        return ApiResponse.success(paymentMethodService.update(id, request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PosPaymentMethodResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(paymentMethodService.getById(id));
    }

    @PutMapping("/{id}/activate")
    public void activate(@PathVariable Long id) {
        paymentMethodService.activate(id);
    }

    @DeleteMapping("/{id}/deactivate")
    public void deactivate(@PathVariable Long id) {
        paymentMethodService.deactivate(id);
    }

    @GetMapping
    public ApiResponse<Page<PosPaymentMethodResponse>> getAll(
            PosPaymentMethodFilter filter,
            @PageableDefault(size = 20, sort = "name") Pageable pageable
    ) {
        return ApiResponse.success(paymentMethodService.getAll(filter, pageable));
    }
}
