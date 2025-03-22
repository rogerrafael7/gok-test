package gok.api.domain.dto;

import gok.api.domain.models.product.ProductModel;

import java.util.List;

public record GetProductsByIdsResponse(
        List<ProductModel> productsFound,
        List<Long> productIdsNotFound
) {
}
