package gok.api.application.services;

import gok.api.domain.models.CategoryModel;
import gok.api.domain.repositories.CategoryRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class CategoryService {
    @Inject
    CategoryRepository categoryRepository;

    public List<CategoryModel> getCategories() {
        return categoryRepository.getCategories();
    }
}
