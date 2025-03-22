package gok.api.domain.repositories;

import gok.api.domain.models.product.ProductModel;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

public abstract class ProductRepository {
    public abstract ProductModel getProductByIdOrFail(Long id) throws ServerException;
    public abstract PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest);
}
