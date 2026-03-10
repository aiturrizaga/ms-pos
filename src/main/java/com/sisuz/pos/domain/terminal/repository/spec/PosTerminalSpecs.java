package com.sisuz.pos.domain.terminal.repository.spec;

import com.sisuz.pos.domain.terminal.entity.PosTerminal;
import org.springframework.data.jpa.domain.Specification;

public final class PosTerminalSpecs {

    private PosTerminalSpecs() {
    }

    public static Specification<PosTerminal> withFilters(PosTerminalSpecFilter filter) {

        return Specification.allOf(
                companyEq(filter.getCompanyId()),
                activeEq(filter.getActive()),
                qLike(filter.getQ()),
                codeEq(filter.getCode()),
                nameLike(filter.getName()),
                storeIdEq(filter.getStoreId())
        );
    }

    public static Specification<PosTerminal> companyEq(java.util.UUID companyId) {
        return (root, query, cb) ->
                companyId == null ? cb.conjunction() : cb.equal(root.get("companyId"), companyId);
    }

    public static Specification<PosTerminal> activeEq(Boolean active) {
        return (root, query, cb) ->
                active == null ? cb.conjunction() : cb.equal(root.get("active"), active);
    }

    public static Specification<PosTerminal> storeIdEq(Long storeId) {
        return (root, query, cb) ->
                storeId == null ? cb.conjunction() : cb.equal(root.get("storeId"), storeId);
    }

    public static Specification<PosTerminal> codeEq(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            return cb.equal(cb.lower(root.get("code")), value.trim().toLowerCase());
        };
    }

    public static Specification<PosTerminal> nameLike(String value) {
        return (root, query, cb) -> {
            if (value == null || value.isBlank()) return cb.conjunction();
            String pattern = "%" + value.trim().toLowerCase() + "%";
            return cb.like(cb.lower(root.get("name")), pattern);
        };
    }

    public static Specification<PosTerminal> qLike(String value) {
        return (root, query, cb) -> {

            if (value == null || value.isBlank()) return cb.conjunction();

            String pattern = "%" + value.trim().toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("name")), pattern),
                    cb.like(cb.lower(root.get("code")), pattern)
            );
        };
    }
}
