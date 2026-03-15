package com.sisuz.pos.domain.config.controller;

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
    public PosConfigResponse create(@RequestBody @Valid PosConfigCreateRequest request) {
        return configService.create(request);
    }

    @PutMapping("/{id}")
    public PosConfigResponse update(@PathVariable Long id, @RequestBody @Valid PosConfigUpdateRequest request) {
        return configService.update(id, request);
    }

    @GetMapping("/store/{storeId}")
    public PosConfigResponse getByStoreId(@PathVariable Long storeId) {
        return configService.getByStoreId(storeId);
    }
}
