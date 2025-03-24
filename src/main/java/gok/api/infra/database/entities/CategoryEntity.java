package gok.api.infra.database.entities;

import jakarta.persistence.*;
import gok.api.domain.models.CategoryModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "categories")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<SubCategoryEntity> subCategories;

    public CategoryModel toModel() {
        var builder = CategoryModel.builder()
                .id(id)
                .name(name);

        if(subCategories != null) {
            builder.subCategories(
                    subCategories.stream().map(subCategoryEntity -> {
                        subCategoryEntity.setCategory(null);
                        return subCategoryEntity.toModel();
                    }).toList()
            );
        }

        return builder.build();
    }
}