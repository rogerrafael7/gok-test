package gok.api.infra.database.repositories;

import gok.api.domain.models.CategoryModel;
import gok.api.domain.repositories.CategoryRepository;
import gok.api.infra.database.entities.CategoryEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class CategoryRepositoryImpl extends CategoryRepository implements PanacheRepository<CategoryEntity> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CategoryModel> getCategories() {
        return entityManager.createQuery(
                "SELECT DISTINCT c FROM categories c " +
                "INNER JOIN FETCH c.subCategories sc " +
                "ORDER BY c.name", CategoryEntity.class)
                .getResultList()
                .stream()
                .map(CategoryEntity::toModel)
                .toList();
    }
}
