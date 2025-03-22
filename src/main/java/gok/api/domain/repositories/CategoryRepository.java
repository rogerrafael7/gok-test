package gok.api.domain.repositories;

import gok.api.domain.models.category.CategoryModel;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;

public abstract class CategoryRepository {
    public abstract CategoryModel getCategoryByIdOrFail(Long id) throws ServerException;
    public abstract PaginationResponse<CategoryModel> getCategories(PaginationRequest paginationRequest);
    public abstract CategoryModel createCategory(String name);
    public abstract void updateCategory(Long id, String name);
    public abstract void deleteCategory(Long id);
} 