package gok.api.infra.database.entities;

import gok.api.domain.models.OrderProductModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "order_products")
@Getter
@Setter
public class OrderProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer quantity;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    public OrderProductModel toModel() {
        var builder = OrderProductModel.builder()
                .id(id)
                .quantity(quantity)
                .unitPrice(unitPrice);

        if (product != null) {
            builder.product(product.toModel());
        }

        return builder.build();
    }
}
