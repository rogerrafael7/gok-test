package gok.api.infra.shared.types;

import java.util.List;

public record PaginationResponse<T>(
        int page,
        int limit,
        long totalItems,
        int totalPages,
        Boolean hasNextPage,
        Boolean hasPreviousPage,
        List<T> data
) {
}
