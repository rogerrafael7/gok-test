package gok.api.domain.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProductRequest(
        Optional<String> name,
        Optional<BigDecimal> price,
        Optional<String> type,
        Optional<String> description
) {
}
