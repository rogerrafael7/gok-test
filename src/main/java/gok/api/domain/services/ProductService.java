package gok.api.domain.services;

import gok.api.domain.models.product.ProductModel;
import gok.api.domain.repositories.ProductRepository;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository productRepository;

    public PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest) {
        return productRepository.getProducts(paginationRequest);
    }

    public ProductModel getProductById(Long id) throws ServerException {
        return productRepository.getProductByIdOrFail(id);
    }
}
