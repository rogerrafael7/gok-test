package gok.api.infra.data.entities;

import jakarta.persistence.*;
import gok.api.domain.models.productcategory.ProductCategoryModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "product_categories")
@Getter
@Setter
public class ProductCategoryEntity extends ProductCategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "product_id")
    private Long productId;

    @Column(nullable = false, name = "category_id")
    private Long categoryId;

}