package gok.api.infra.data.repositories;

import gok.api.domain.models.product.ProductModel;
import gok.api.domain.repositories.ProductRepository;
import gok.api.infra.data.entities.ProductEntity;
import gok.api.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class ProductRepositoryImpl extends ProductRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ProductModel getProductByIdOrFail(Long id) throws ServerException {
        var productEntity = entityManager.find(ProductEntity.class, id);
        if (productEntity == null) {
            throw new ServerException("Product not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        return productEntity;
    }

    @Override
    public PaginationResponse<ProductModel> getProducts(PaginationRequest paginationRequest) {
        var query = entityManager.createQuery("SELECT p FROM products p", ProductModel.class);
        var page = paginationRequest.getPage();
        var limit = paginationRequest.getLimit();
        int offset = (page - 1) * limit;

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<ProductModel> products = query.getResultList();

        Long count = entityManager.createQuery("SELECT COUNT(p) FROM products p", Long.class).getSingleResult();

        var totalItems = count.intValue();
        var totalPages = (int) Math.ceil((double) totalItems / limit);
        var hasNextPage = page < totalPages;
        var hasPreviousPage = page > 1;

        return new PaginationResponse<>(
                page,
                limit,
                totalItems,
                totalPages,
                hasNextPage,
                hasPreviousPage,
                products
        );
    }


}
