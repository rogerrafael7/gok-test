package gok.api.domain.dto;

import gok.api.domain.models.product.ProductModel;

import java.util.List;
import java.util.UUID;

public record GetProductsByIdsResponse(
        List<ProductModel> productsFound,
        List<UUID> productIdsNotFound
) {
}
