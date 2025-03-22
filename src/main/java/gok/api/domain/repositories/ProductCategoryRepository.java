package gok.api.domain.repositories;

import gok.api.domain.models.productcategory.ProductCategoryModel;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;

public abstract class ProductCategoryRepository {
    public abstract ProductCategoryModel getProductCategoryByIdOrFail(Long id) throws ServerException;
    public abstract List<ProductCategoryModel> getProductCategoriesByProductId(Long productId);
    public abstract List<ProductCategoryModel> getProductCategoriesByCategoryId(Long categoryId);
    public abstract PaginationResponse<ProductCategoryModel> getProductCategories(PaginationRequest paginationRequest);
    public abstract ProductCategoryModel createProductCategory(Long productId, Long categoryId);
    public abstract void deleteProductCategory(Long id);
} 