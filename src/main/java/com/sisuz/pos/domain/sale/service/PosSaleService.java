package com.sisuz.pos.domain.sale.service;

import com.sisuz.pos.domain.sale.controller.dto.PosSaleCreateRequest;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleDetailResponse;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleFilter;
import com.sisuz.pos.domain.sale.controller.dto.PosSaleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PosSaleService {

    PosSaleDetailResponse createSale(PosSaleCreateRequest request);

    PosSaleDetailResponse getSaleDetailById(Long id);

    PosSaleResponse getSaleById(Long id);

    Page<PosSaleResponse> getAll(PosSaleFilter filter, Pageable pageable);
}
