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
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public ProductModel toModel() {
        return ProductModel.builder()
                .id(id)
                .name(name)
                .price(price)
                .category(category.toModel())
                .build();
    }
}
