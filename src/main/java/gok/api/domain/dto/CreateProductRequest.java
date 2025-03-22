package gok.api.domain.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record CreateProductRequest(String name, BigDecimal price, String type, Optional<String> description) {
}
