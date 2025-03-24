package gok.api.infra.database.entities;

import jakarta.persistence.*;
import gok.api.domain.models.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "products")
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategoryEntity subCategory;

    public ProductModel toModel() {
        return ProductModel.builder()
                .id(id)
                .name(name)
                .currentPrice(price)
                .subCategory(subCategory.toModel())
                .build();
    }
}
