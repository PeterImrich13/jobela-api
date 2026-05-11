package com.jobela.jobela_api.common.pagination;

import com.jobela.jobela_api.common.exception.InvalidPaginationException;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public final class PaginationUtils {

    private static final int MAX_PAGE_SIZE = 100;

    private PaginationUtils() {
    }

    public static void validatePageable(Pageable pageable, Set<String> allowedSortFields) {

        validatePageSize(pageable);
        validateSortFields(pageable, allowedSortFields);
    }
        public static void validatePageSize(Pageable pageable) {
        if (pageable.getPageSize() > MAX_PAGE_SIZE) {
            throw new InvalidPaginationException( "Page size must not be greater than "
            + MAX_PAGE_SIZE);
        }

        if (pageable.getPageNumber() < 0) {
            throw new InvalidPaginationException("Page number must not be negative");
        }
    }

    private static void validateSortFields(Pageable pageable, Set<String> allowedSortFields) {
        for (var order : pageable.getSort()) {
            if (!allowedSortFields.contains(order.getProperty())) {
                throw new InvalidPaginationException("Sorting by field '" + order.getProperty() +
                        "' is not allowed");
            }
        }
    }
}
