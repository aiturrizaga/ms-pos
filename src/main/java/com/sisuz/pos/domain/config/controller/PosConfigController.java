package com.sisuz.pos.domain.config.controller;

import com.sisuz.pos.common.api.ApiResponse;
import com.sisuz.pos.domain.config.controller.dto.PosConfigCreateRequest;
import com.sisuz.pos.domain.config.controller.dto.PosConfigResponse;
import com.sisuz.pos.domain.config.controller.dto.PosConfigUpdateRequest;
import com.sisuz.pos.domain.config.service.PosConfigService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/configs")
@RequiredArgsConstructor
public class PosConfigController {

    private final PosConfigService configService;

    @PostMapping
    public ApiResponse<PosConfigResponse> create(@RequestBody @Valid PosConfigCreateRequest request) {
        return ApiResponse.success(configService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PosConfigResponse> update(@PathVariable Long id, @RequestBody @Valid PosConfigUpdateRequest request) {
        return ApiResponse.success(configService.update(id, request));
    }

    @GetMapping("/store/{storeId}")
    public ApiResponse<PosConfigResponse> getByStoreId(@PathVariable Long storeId) {
        return ApiResponse.success(configService.getByStoreId(storeId));
    }
}
