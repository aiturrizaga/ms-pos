package com.sisuz.pos.domain.sale.service;

import com.sisuz.pos.common.exception.BusinessException;
import com.sisuz.pos.common.exception.NotFoundException;
import com.sisuz.pos.domain.cash.entity.PosCashMovement;
import com.sisuz.pos.domain.cash.repository.PosCashMovementRepository;
import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import com.sisuz.pos.domain.config.repository.PosPaymentMethodRepository;
import com.sisuz.pos.domain.sale.controller.dto.*;
import com.sisuz.pos.domain.sale.entity.PosSale;
import com.sisuz.pos.domain.sale.entity.PosSaleLine;
import com.sisuz.pos.domain.sale.entity.PosSalePayment;
import com.sisuz.pos.domain.sale.mapper.PosSaleMapper;
import com.sisuz.pos.domain.sale.repository.PosSaleLineRepository;
import com.sisuz.pos.domain.sale.repository.PosSalePaymentRepository;
import com.sisuz.pos.domain.sale.repository.PosSaleRepository;
import com.sisuz.pos.domain.sale.repository.spec.PosSaleSpecFilter;
import com.sisuz.pos.domain.sale.repository.spec.PosSaleSpecs;
import com.sisuz.pos.domain.session.entity.PosSession;
import com.sisuz.pos.domain.session.repository.PosSessionRepository;
import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import com.sisuz.pos.domain.terminal.repository.PosTerminalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PosSaleServiceImpl implements PosSaleService {

    private static final String SESSION_OPEN = "OPEN";
    private static final String SALE_STATUS_PAID = "PAID";
    private static final String DOC_SALE_NOTE = "SALE_NOTE";
    private static final String CASH_MOVEMENT_SALE_PAYMENT = "SALE_PAYMENT";

    private final PosSaleRepository saleRepository;
    private final PosSaleLineRepository saleLineRepository;
    private final PosSalePaymentRepository salePaymentRepository;
    private final PosCashMovementRepository cashMovementRepository;
    private final PosSessionRepository sessionRepository;
    private final PosTerminalRepository terminalRepository;
    private final PosPaymentMethodRepository paymentMethodRepository;
    private final PosSaleMapper mapper;

    @Override
    @Transactional
    public PosSaleDetailResponse createSale(PosSaleCreateRequest request) {

        PosSession session = sessionRepository.findById(request.sessionId())
                .orElseThrow(() -> NotFoundException.of("PosSession", request.sessionId()));

        PosTerminal terminal = terminalRepository.findById(request.terminalId())
                .orElseThrow(() -> NotFoundException.of("PosTerminal", request.terminalId()));

        validateCreateSale(request, session, terminal);

        PosSale draftSale = new PosSale();
        draftSale.setSession(session);
        draftSale.setTerminal(terminal);
        draftSale.setSaleNumber(generateSaleNumber(session, terminal));
        draftSale.setCustomerId(request.customerId());
        draftSale.setStatus(SALE_STATUS_PAID);
        draftSale.setCurrencyCode(request.currencyCode().trim().toUpperCase());
        draftSale.setSubtotal(zeroIfNull(request.subtotal()));
        draftSale.setDiscountTotal(zeroIfNull(request.discountTotal()));
        draftSale.setTaxTotal(zeroIfNull(request.taxTotal()));
        draftSale.setTotal(zeroIfNull(request.total()));
        draftSale.setPaidAt(Instant.now());
        draftSale.setCashierId(request.cashierId().trim());
        draftSale.setNote(trimToNull(request.note()));
        draftSale.setDocumentType(resolveDocumentType(request.documentType()));
        draftSale.setDocumentId(null);

        final PosSale sale = saleRepository.save(draftSale);

        List<PosSaleLine> lines = request.lines().stream()
                .map(line -> buildSaleLine(sale, line))
                .toList();
        saleLineRepository.saveAll(lines);

        List<PosSalePayment> payments = request.payments().stream()
                .map(payment -> buildSalePayment(sale, session, payment))
                .toList();
        salePaymentRepository.saveAll(payments);

        List<PosCashMovement> cashMovements = payments.stream()
                .map(payment -> buildCashMovementFromPayment(sale, session, terminal, payment))
                .toList();
        cashMovementRepository.saveAll(cashMovements);

        return buildDetailResponse(sale, lines, payments);
    }

    @Override
    @Transactional(readOnly = true)
    public PosSaleDetailResponse getSaleDetailById(Long id) {
        PosSale sale = saleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("PosSale", id));

        List<PosSaleLine> lines = saleLineRepository.findAllBySaleId(id);
        List<PosSalePayment> payments = salePaymentRepository.findAllBySaleId(id);

        return buildDetailResponse(sale, lines, payments);
    }

    @Override
    @Transactional(readOnly = true)
    public PosSaleResponse getSaleById(Long id) {
        PosSale sale = saleRepository.findById(id)
                .orElseThrow(() -> NotFoundException.of("PosSale", id));

        return mapper.toResponse(sale);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PosSaleResponse> getAll(PosSaleFilter filter, Pageable pageable) {
        PosSaleSpecFilter specFilter = PosSaleSpecFilter.from(filter);
        Specification<PosSale> spec = PosSaleSpecs.withFilters(specFilter);

        return saleRepository.findAll(spec, pageable)
                .map(mapper::toResponse);
    }

    private void validateCreateSale(PosSaleCreateRequest request, PosSession session, PosTerminal terminal) {
        if (!SESSION_OPEN.equalsIgnoreCase(session.getStatus())) {
            throw new BusinessException(4301, "Sale can only be created for an OPEN session");
        }

        if (!Objects.equals(session.getTerminal().getId(), terminal.getId())) {
            throw new BusinessException(4302, "Session does not belong to the provided terminal");
        }

        if (request.lines() == null || request.lines().isEmpty()) {
            throw new BusinessException(4303, "Sale must contain at least one line");
        }

        if (request.payments() == null || request.payments().isEmpty()) {
            throw new BusinessException(4304, "Sale must contain at least one payment");
        }

        BigDecimal linesTotal = request.lines().stream()
                .map(PosSaleLineCreateRequest::lineTotal)
                .map(this::zeroIfNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (linesTotal.compareTo(zeroIfNull(request.total())) != 0) {
            throw new BusinessException(4305, "Sale total does not match the sum of line totals");
        }

        BigDecimal paymentsTotal = request.payments().stream()
                .map(PosSalePaymentCreateRequest::amount)
                .map(this::zeroIfNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (paymentsTotal.compareTo(zeroIfNull(request.total())) != 0) {
            throw new BusinessException(4306, "Payments total does not match sale total");
        }

        request.lines().forEach(this::validateLine);
        request.payments().forEach(payment -> validatePayment(request, payment));
    }

    private void validateLine(PosSaleLineCreateRequest line) {
        if (line.skuId() == null) {
            throw new BusinessException(4307, "Sale line skuId is required");
        }
        if (line.skuName() == null || line.skuName().isBlank()) {
            throw new BusinessException(4308, "Sale line skuName is required");
        }
        if (line.qty() == null || line.qty().signum() <= 0) {
            throw new BusinessException(4309, "Sale line qty must be greater than zero");
        }
        if (line.unitPrice() == null || line.unitPrice().signum() < 0) {
            throw new BusinessException(4310, "Sale line unitPrice is invalid");
        }
        if (line.lineTotal() == null || line.lineTotal().signum() < 0) {
            throw new BusinessException(4311, "Sale line total is invalid");
        }
    }

    private void validatePayment(PosSaleCreateRequest saleRequest, PosSalePaymentCreateRequest paymentRequest) {
        PosPaymentMethod paymentMethod = paymentMethodRepository.findById(paymentRequest.paymentMethodId())
                .orElseThrow(() -> NotFoundException.of("PosPaymentMethod", paymentRequest.paymentMethodId()));

        if (!paymentMethod.isActive()) {
            throw new BusinessException(4312, "Payment method is inactive");
        }

        if (paymentRequest.amount() == null || paymentRequest.amount().signum() <= 0) {
            throw new BusinessException(4313, "Payment amount must be greater than zero");
        }

        if (paymentRequest.currencyCode() == null || paymentRequest.currencyCode().isBlank()) {
            throw new BusinessException(4314, "Payment currency code is required");
        }

        if (!saleRequest.currencyCode().trim().equalsIgnoreCase(paymentRequest.currencyCode().trim())) {
            throw new BusinessException(4315, "Payment currency must match sale currency");
        }

        if (Boolean.TRUE.equals(paymentMethod.getRequiresReference())
                && (paymentRequest.reference() == null || paymentRequest.reference().isBlank())) {
            throw new BusinessException(4316, "Reference is required for payment method: " + paymentMethod.getName());
        }

        if (paymentRequest.receivedBy() == null || paymentRequest.receivedBy().isBlank()) {
            throw new BusinessException(4317, "Payment receivedBy is required");
        }
    }

    private PosSaleLine buildSaleLine(PosSale sale, PosSaleLineCreateRequest request) {
        PosSaleLine line = new PosSaleLine();
        line.setSale(sale);
        line.setSkuId(request.skuId());
        line.setSkuName(request.skuName().trim());
        line.setQty(request.qty());
        line.setUnitPrice(request.unitPrice());
        line.setDiscountAmount(zeroIfNull(request.discountAmount()));
        line.setTaxAmount(zeroIfNull(request.taxAmount()));
        line.setLineTotal(zeroIfNull(request.lineTotal()));
        line.setNote(trimToNull(request.note()));
        return line;
    }

    private PosSalePayment buildSalePayment(PosSale sale, PosSession session, PosSalePaymentCreateRequest request) {
        PosPaymentMethod paymentMethod = paymentMethodRepository.findById(request.paymentMethodId())
                .orElseThrow(() -> NotFoundException.of("PosPaymentMethod", request.paymentMethodId()));

        PosSalePayment payment = new PosSalePayment();
        payment.setSale(sale);
        payment.setSession(session);
        payment.setPaymentMethod(paymentMethod);
        payment.setAmount(request.amount());
        payment.setCurrencyCode(request.currencyCode().trim().toUpperCase());
        payment.setReference(trimToNull(request.reference()));
        payment.setPaidAt(Instant.now());
        payment.setReceivedBy(request.receivedBy().trim());
        return payment;
    }

    private PosCashMovement buildCashMovementFromPayment(
            PosSale sale,
            PosSession session,
            PosTerminal terminal,
            PosSalePayment payment
    ) {
        PosCashMovement movement = new PosCashMovement();
        movement.setSession(session);
        movement.setTerminal(terminal);
        movement.setDrawer(session.getDrawer());
        movement.setMovementType(CASH_MOVEMENT_SALE_PAYMENT);
        movement.setPaymentMethod(payment.getPaymentMethod());
        movement.setAmount(payment.getAmount());
        movement.setCurrencyCode(payment.getCurrencyCode());
        movement.setOccurredAt(payment.getPaidAt());
        movement.setReasonCode("SALE");
        movement.setNote("Sale payment: " + sale.getSaleNumber());
        movement.setIsVoided(false);
        return movement;
    }

    private PosSaleDetailResponse buildDetailResponse(
            PosSale sale,
            List<PosSaleLine> lines,
            List<PosSalePayment> payments
    ) {
        List<PosSaleLineResponse> lineResponses = lines.stream()
                .map(mapper::toLineResponse)
                .toList();

        List<PosSalePaymentResponse> paymentResponses = payments.stream()
                .map(mapper::toPaymentResponse)
                .toList();

        return new PosSaleDetailResponse(
                sale.getId(),
                sale.getSession().getId(),
                sale.getTerminal().getId(),
                sale.getSaleNumber(),
                sale.getCustomerId(),
                sale.getStatus(),
                sale.getCurrencyCode(),
                sale.getSubtotal(),
                sale.getDiscountTotal(),
                sale.getTaxTotal(),
                sale.getTotal(),
                sale.getPaidAt(),
                sale.getCashierId(),
                sale.getNote(),
                sale.getDocumentType(),
                sale.getDocumentId(),
                lineResponses,
                paymentResponses
        );
    }

    private String resolveDocumentType(String requestedDocumentType) {
        if (requestedDocumentType == null || requestedDocumentType.isBlank()) {
            return DOC_SALE_NOTE;
        }
        return requestedDocumentType.trim().toUpperCase();
    }

    private String generateSaleNumber(PosSession session, PosTerminal terminal) {
        return "S-" + terminal.getId() + "-" + System.currentTimeMillis();
    }

    private BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    private String trimToNull(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isBlank() ? null : trimmed;
    }
}
