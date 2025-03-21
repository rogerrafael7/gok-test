package gok.api.domain.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import gok.api.domain.dto.CreateProductRequest;
import gok.api.domain.dto.GetProductsByIdsResponse;
import gok.api.domain.dto.UpdateProductRequest;
import gok.api.domain.models.product.ProductModel;
import gok.api.domain.repositories.ProductRepository;
import gok.api.domain.repositories.dto.CreateProductModelRequest;
import gok.api.domain.repositories.dto.UpdateProductModelRequest;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository productRepository;

    public PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest) {
        return productRepository.getProducts(paginationRequest);
    }

    public ProductModel getProductById(UUID id) throws ServerException {
        return productRepository.getProductByIdOrFail(id);
    }

    public ProductModel createProduct(CreateProductRequest request) {
        var productToSave = new CreateProductModelRequest(request.name(), request.price(), request.type(), request.description());
        return productRepository.createProduct(productToSave);
    }

    public void updateProduct(UUID id, UpdateProductRequest request) throws ServerException {
        var productToUpdate = new UpdateProductModelRequest(request.name(), request.price(), request.type(), request.description());
        productRepository.updateProduct(id, productToUpdate);
    }

    public void deleteProduct(UUID id) throws ServerException {
        productRepository.deleteProduct(id);
    }

    public GetProductsByIdsResponse getProductsByIds(List<UUID> ids) {
        return productRepository.getProductModelsByIds(ids);
    }
}
