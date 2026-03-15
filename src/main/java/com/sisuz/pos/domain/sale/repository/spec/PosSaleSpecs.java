package com.sisuz.pos.domain.sale.repository.spec;

import com.sisuz.pos.domain.sale.entity.PosSale;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public final class PosSaleSpecs {

    private PosSaleSpecs() {
    }

    public static Specification<PosSale> withFilters(PosSaleSpecFilter filter) {
        return Specification.allOf(
                sessionIdEq(filter.getSessionId()),
                terminalIdEq(filter.getTerminalId()),
                customerIdEq(filter.getCustomerId()),
                statusEq(filter.getStatus()),
                currencyCodeEq(filter.getCurrencyCode()),
                cashierIdEq(filter.getCashierId()),
                documentTypeEq(filter.getDocumentType()),
                paidAtBetween(filter.getPaidFrom(), filter.getPaidTo()),
                qLike(filter.getQ())
        );
    }

    public static Specification<PosSale> sessionIdEq(Long value) {
        return (root, query, cb) ->
                value == null ? cb.conjunction() : cb.equal(root.get("session").get("id"), value);
    }

    public static Specification<PosSale> terminalIdEq(Long value) {
        return (root, query, cb) ->
                value == null ? cb.conjunction() : cb.equal(root.get("terminal").get("id"), value);
    }

    public static Specification<PosSale> customerIdEq(Long value) {
        return (root, query, cb) ->
                value == null ? cb.conjunction() : cb.equal(root.get("customerId"), value);
    }

    public static Specification<PosSale> statusEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("status")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosSale> currencyCodeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("currencyCode")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosSale> cashierIdEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("cashierId")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosSale> documentTypeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("documentType")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosSale> paidAtBetween(Instant from, Instant to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return cb.conjunction();
            if (from != null && to != null) return cb.between(root.get("paidAt"), from, to);
            if (from != null) return cb.greaterThanOrEqualTo(root.get("paidAt"), from);
            return cb.lessThanOrEqualTo(root.get("paidAt"), to);
        };
    }

    public static Specification<PosSale> qLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            String pattern = "%" + value.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("saleNumber")), pattern),
                    cb.like(cb.lower(root.get("cashierId")), pattern),
                    cb.like(cb.lower(root.get("note")), pattern)
            );
        };
    }
}
