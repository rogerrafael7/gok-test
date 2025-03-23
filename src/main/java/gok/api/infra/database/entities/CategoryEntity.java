package gok.api.infra.database.entities;

import jakarta.persistence.*;
import gok.api.domain.models.CategoryModel;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "categories")
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public CategoryModel toModel() {
        return CategoryModel.builder()
                .id(id)
                .name(name)
                .build();
    }
}