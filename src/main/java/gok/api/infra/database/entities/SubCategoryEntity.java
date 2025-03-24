package gok.api.infra.database.entities;

import jakarta.persistence.*;
import gok.api.domain.models.SubCategoryModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Entity(name = "sub_categories")
@Getter
@Setter
public class SubCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public SubCategoryModel toModel() {
        return SubCategoryModel.builder()
                .id(id)
                .name(name)
                .categoryId(Optional.ofNullable(category).map(CategoryEntity::getId).orElse(null))
                .build();
    }
}