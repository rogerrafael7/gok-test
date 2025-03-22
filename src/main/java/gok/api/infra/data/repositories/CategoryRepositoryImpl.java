package gok.api.infra.data.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import gok.api.domain.models.category.CategoryModel;
import gok.api.domain.repositories.CategoryRepository;
import gok.api.infra.data.entities.CategoryEntity;
import gok.api.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.List;

@ApplicationScoped
public class CategoryRepositoryImpl extends CategoryRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public CategoryModel getCategoryByIdOrFail(Long id) throws ServerException {
        var categoryEntity = entityManager.find(CategoryEntity.class, id);
        if (categoryEntity == null) {
            throw new ServerException("Category not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        return categoryEntity;
    }

    @Override
    public PaginationResponse<CategoryModel> getCategories(PaginationRequest paginationRequest) {
        var query = entityManager.createQuery("SELECT c FROM categories c", CategoryModel.class);
        var page = paginationRequest.getPage();
        var limit = paginationRequest.getLimit();
        int offset = (page - 1) * limit;

        query.setFirstResult(offset);
        query.setMaxResults(limit);
        List<CategoryModel> categories = query.getResultList();

        Long count = entityManager.createQuery("SELECT COUNT(c) FROM categories c", Long.class).getSingleResult();

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
                categories
        );
    }

    @Transactional
    @Override
    public CategoryModel createCategory(String name) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        entityManager.persist(categoryEntity);
        return categoryEntity;
    }

    @Transactional
    @Override
    public void updateCategory(Long id, String name) {
        CategoryEntity categoryEntity = entityManager.find(CategoryEntity.class, id);
        if (categoryEntity == null) {
            throw new ServerException("Category not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        if (name != null) {
            categoryEntity.setName(name);
        }
        entityManager.persist(categoryEntity);
    }

    @Transactional
    @Override
    public void deleteCategory(Long id) {
        CategoryEntity categoryEntity = entityManager.find(CategoryEntity.class, id);
        if (categoryEntity == null) {
            throw new ServerException("Category not found", SERVER_EXCEPTION_CAUSE.BAD_REQUEST);
        }
        entityManager.remove(categoryEntity);
    }
} 