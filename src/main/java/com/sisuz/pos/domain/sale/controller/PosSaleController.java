package com.sisuz.pos.domain.sale.controller;

import com.sisuz.pos.common.api.ApiResponse;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleCreateRequest;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleDetailResponse;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleFilter;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleResponse;
import com.sisuz.pos.domain.sale.service.PosSaleReceiptService;
import com.sisuz.pos.domain.sale.service.PosSaleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sales")
@RequiredArgsConstructor
public class PosSaleController {

    private final PosSaleService saleService;
    private final PosSaleReceiptService receiptService;

    @PostMapping
    public ApiResponse<PosSaleDetailResponse> createSale(@RequestBody @Valid PosSaleCreateRequest request) {
        return ApiResponse.success(saleService.createSale(request));
    }

    @GetMapping("/{id}")
    public ApiResponse<PosSaleResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(saleService.getSaleById(id));
    }

    @GetMapping("/{id}/detail")
    public ApiResponse<PosSaleDetailResponse> getDetailById(@PathVariable Long id) {
        return ApiResponse.success(saleService.getSaleDetailById(id));
    }

    @GetMapping
    public ApiResponse<Page<PosSaleResponse>> getAll(
            PosSaleFilter filter,
            @PageableDefault(size = 20, sort = "paidAt") Pageable pageable
    ) {
        return ApiResponse.success(saleService.getAll(filter, pageable));
    }

    @GetMapping("/{id}/receipt")
    public ResponseEntity<byte[]> receipt(@PathVariable Long id) {
        byte[] pdf = receiptService.generate(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=recibo-" + id + ".pdf")
                .body(pdf);
    }
}
