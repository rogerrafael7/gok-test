package gok.api.infra.database.repositories;

import gok.api.domain.models.CategoryModel;
import gok.api.domain.repositories.CategoryRepository;
import gok.api.infra.database.entities.CategoryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CategoryRepositoryImpl extends CategoryRepository implements PanacheRepository<CategoryEntity> {

    @Override
    public List<CategoryModel> getCategories() {
        return find("ORDER BY name").list().stream().map(CategoryEntity::toModel).toList();
    }
}
