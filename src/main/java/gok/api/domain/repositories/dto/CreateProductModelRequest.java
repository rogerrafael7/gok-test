package gok.api.domain.repositories.dto;

import java.math.BigDecimal;
import java.util.Optional;

public record CreateProductModelRequest(String name, BigDecimal price) {
}
