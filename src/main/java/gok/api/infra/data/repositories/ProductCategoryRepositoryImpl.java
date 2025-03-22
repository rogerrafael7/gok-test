package gok.api.infra.data.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import gok.api.domain.models.productcategory.ProductCategoryModel;
import gok.api.domain.repositories.ProductCategoryRepository;
import gok.api.infra.data.entities.ProductCategoryEntity;
import gok.api.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;

@ApplicationScoped
public class ProductCategoryRepositoryImpl extends ProductCategoryRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ProductCategoryModel getProductCategoryByIdOrFail(Long id) throws ServerException {
        var productCategoryEntity = entityManager.find(ProductCategoryEntity.class, id);
        if (productCategoryEntity == null) {
            throw new ServerException("ProductCategory not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        return productCategoryEntity;
    }

    @Override
    public List<ProductCategoryModel> getProductCategoriesByProductId(Long productId) {
        return entityManager.createQuery(
                "SELECT pc FROM product_categories pc WHERE pc.productId = :productId", 
                ProductCategoryModel.class)
                .setParameter("productId", productId)
                .getResultList();
    }

    @Override
    public List<ProductCategoryModel> getProductCategoriesByCategoryId(Long categoryId) {
        return entityManager.createQuery(
                "SELECT pc FROM product_categories pc WHERE pc.categoryId = :categoryId", 
                ProductCategoryModel.class)
                .setParameter("categoryId", categoryId)
                .getResultList();
    }

    @Override
    public PaginationResponse<ProductCategoryModel> getProductCategories(PaginationRequest paginationRequest) {
        var query = entityManager.createQuery("SELECT pc FROM product_categories pc", ProductCategoryModel.class);
        var page = paginationRequest.getPage();
        var limit = paginationRequest.getLimit();
        int offset = (page - 1) * limit;

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<ProductCategoryModel> productCategories = query.getResultList();

        Long count = entityManager.createQuery("SELECT COUNT(pc) FROM product_categories pc", Long.class).getSingleResult();

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
                productCategories
        );
    }

    @Transactional
    @Override
    public ProductCategoryModel createProductCategory(Long productId, Long categoryId) {
        ProductCategoryEntity productCategoryEntity = new ProductCategoryEntity();
        productCategoryEntity.setProductId(productId);
        productCategoryEntity.setCategoryId(categoryId);
        entityManager.persist(productCategoryEntity);
        return productCategoryEntity;
    }

    @Transactional
    @Override
    public void deleteProductCategory(Long id) {
        ProductCategoryEntity productCategoryEntity = entityManager.find(ProductCategoryEntity.class, id);
        if (productCategoryEntity == null) {
            throw new ServerException("ProductCategory not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        entityManager.remove(productCategoryEntity);
    }
} 