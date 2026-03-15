package com.sisuz.pos.domain.sale.controller;

import com.sisuz.pos.domain.sale.controller.dto.PosSaleCreateRequest;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleDetailResponse;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleFilter;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleResponse;
import com.sisuz.pos.domain.sale.service.PosSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sales")
@RequiredArgsConstructor
public class PosSaleController {

    private final PosSaleService service;

    @PostMapping
    public PosSaleDetailResponse createSale(@RequestBody @Valid PosSaleCreateRequest request) {
        return service.createSale(request);
    }

    @GetMapping("/{id}")
    public PosSaleResponse getById(@PathVariable Long id) {
        return service.getSaleById(id);
    }

    @GetMapping("/{id}/detail")
    public PosSaleDetailResponse getDetailById(@PathVariable Long id) {
        return service.getSaleDetailById(id);
    }

    @GetMapping
    public Page<PosSaleResponse> getAll(
            PosSaleFilter filter,
            @PageableDefault(size = 20, sort = "paidAt") Pageable pageable
    ) {
        return service.getAll(filter, pageable);
    }
}
