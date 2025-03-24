package gok.api.domain.repositories;

import gok.api.domain.models.CategoryModel;

import java.util.List;

public abstract class CategoryRepository {
    public abstract List<CategoryModel> getCategories();
}
