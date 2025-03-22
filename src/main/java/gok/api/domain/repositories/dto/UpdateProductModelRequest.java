package gok.api.domain.repositories.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record UpdateProductModelRequest(
        Optional<String> name,
        Optional<BigDecimal> price) {
}
