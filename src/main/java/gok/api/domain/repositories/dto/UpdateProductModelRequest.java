package gok.api.domain.repositories.dto;

import java.util.Optional;

public record UpdateProductModelRequest(
        Optional<String> name,
        Optional<Double> price,
        Optional<String> type,
        Optional<String> description) {
}
