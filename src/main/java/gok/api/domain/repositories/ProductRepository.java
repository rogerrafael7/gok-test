package gok.api.domain.repositories;

import gok.api.domain.dto.GetProductsByIdsResponse;
import gok.api.domain.models.product.ProductModel;
import gok.api.domain.repositories.dto.CreateProductModelRequest;
import gok.api.domain.repositories.dto.UpdateProductModelRequest;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;
import java.util.UUID;

public abstract class ProductRepository {
    public abstract ProductModel getProductByIdOrFail(UUID id) throws ServerException;
    public abstract GetProductsByIdsResponse getProductModelsByIds(List<UUID> id);
    public abstract PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest);
    public abstract ProductModel createProduct(CreateProductModelRequest product);
    public abstract void updateProduct(UUID id, UpdateProductModelRequest product);
    public abstract void deleteProduct(UUID id);
}
