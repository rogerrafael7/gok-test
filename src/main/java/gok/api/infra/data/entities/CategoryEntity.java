package gok.api.infra.data.entities;

import jakarta.persistence.*;
import gok.api.domain.models.category.CategoryModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "categories")
@Getter
@Setter
public class CategoryEntity extends CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

}