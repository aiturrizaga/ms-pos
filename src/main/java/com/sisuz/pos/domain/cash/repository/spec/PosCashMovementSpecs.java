package com.sisuz.pos.domain.cash.repository.spec;

import com.sisuz.pos.domain.cash.entity.PosCashMovement;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public final class PosCashMovementSpecs {

    private PosCashMovementSpecs() {
    }

    public static Specification<PosCashMovement> withFilters(PosCashMovementSpecFilter filter) {
        return Specification.allOf(
                sessionIdEq(filter.getSessionId()),
                terminalIdEq(filter.getTerminalId()),
                drawerIdEq(filter.getDrawerId()),
                movementTypeEq(filter.getMovementType()),
                paymentMethodIdEq(filter.getPaymentMethodId()),
                currencyCodeEq(filter.getCurrencyCode()),
                isVoidedEq(filter.getIsVoided()),
                reasonCodeEq(filter.getReasonCode()),
                occurredAtBetween(filter.getOccurredFrom(), filter.getOccurredTo()),
                qLike(filter.getQ())
        );
    }

    public static Specification<PosCashMovement> sessionIdEq(Long sessionId) {
        return (root, query, cb) ->
                sessionId == null ? cb.conjunction() : cb.equal(root.get("session").get("id"), sessionId);
    }

    public static Specification<PosCashMovement> terminalIdEq(Long terminalId) {
        return (root, query, cb) ->
                terminalId == null ? cb.conjunction() : cb.equal(root.get("terminal").get("id"), terminalId);
    }

    public static Specification<PosCashMovement> drawerIdEq(Long drawerId) {
        return (root, query, cb) ->
                drawerId == null ? cb.conjunction() : cb.equal(root.get("drawer").get("id"), drawerId);
    }

    public static Specification<PosCashMovement> movementTypeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("movementType")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosCashMovement> paymentMethodIdEq(Long paymentMethodId) {
        return (root, query, cb) ->
                paymentMethodId == null ? cb.conjunction() : cb.equal(root.get("paymentMethod").get("id"), paymentMethodId);
    }

    public static Specification<PosCashMovement> currencyCodeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("currencyCode")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosCashMovement> isVoidedEq(Boolean isVoided) {
        return (root, query, cb) ->
                isVoided == null ? cb.conjunction() : cb.equal(root.get("isVoided"), isVoided);
    }

    public static Specification<PosCashMovement> reasonCodeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("reasonCode")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosCashMovement> occurredAtBetween(Instant from, Instant to) {
        return (root, query, cb) -> {
            if (from == null && to == null) return cb.conjunction();
            if (from != null && to != null) return cb.between(root.get("occurredAt"), from, to);
            if (from != null) return cb.greaterThanOrEqualTo(root.get("occurredAt"), from);
            return cb.lessThanOrEqualTo(root.get("occurredAt"), to);
        };
    }

    public static Specification<PosCashMovement> qLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();

            String pattern = "%" + value.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("movementType")), pattern),
                    cb.like(cb.lower(root.get("currencyCode")), pattern),
                    cb.like(cb.lower(root.get("reasonCode")), pattern),
                    cb.like(cb.lower(root.get("note")), pattern),
                    cb.like(cb.lower(root.get("paymentMethod").get("code")), pattern),
                    cb.like(cb.lower(root.get("paymentMethod").get("name")), pattern)
            );
        };
    }
}
