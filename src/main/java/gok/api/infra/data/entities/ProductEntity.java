package gok.api.infra.data.entities;

import jakarta.persistence.*;
import gok.api.domain.models.product.ProductModel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "products")
@Getter
@Setter
public class ProductEntity extends ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

}
