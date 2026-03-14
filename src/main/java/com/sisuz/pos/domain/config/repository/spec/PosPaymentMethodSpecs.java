package com.sisuz.pos.domain.config.repository.spec;

import com.sisuz.pos.domain.config.entity.PosPaymentMethod;
import org.springframework.data.jpa.domain.Specification;

public final class PosPaymentMethodSpecs {

    private PosPaymentMethodSpecs() {
    }

    public static Specification<PosPaymentMethod> withFilters(PosPaymentMethodSpecFilter filter) {
        return Specification.allOf(
                activeEq(filter.getActive()),
                qLike(filter.getQ()),
                codeEq(filter.getCode()),
                nameLike(filter.getName()),
                methodTypeEq(filter.getMethodType()),
                requiresReferenceEq(filter.getRequiresReference())
        );
    }

    public static Specification<PosPaymentMethod> activeEq(Boolean active) {
        return (root, query, cb) ->
                active == null ? cb.conjunction() : cb.equal(root.get("active"), active);
    }

    public static Specification<PosPaymentMethod> requiresReferenceEq(Boolean requiresReference) {
        return (root, query, cb) ->
                requiresReference == null ? cb.conjunction() : cb.equal(root.get("requiresReference"), requiresReference);
    }

    public static Specification<PosPaymentMethod> codeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("code")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosPaymentMethod> nameLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            String pattern = "%" + value.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), pattern);
        };
    }

    public static Specification<PosPaymentMethod> methodTypeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("methodType")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosPaymentMethod> qLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();

            String pattern = "%" + value.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("code")), pattern),
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("methodType")), pattern)
            );
        };
    }

    public static Specification<PosPaymentMethod> idEq(Long id) {
        return (root, query, cb) ->
                id == null ? cb.conjunction() : cb.equal(root.get("id"), id);
    }
}
