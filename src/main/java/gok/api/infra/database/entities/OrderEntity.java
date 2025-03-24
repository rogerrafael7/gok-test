package gok.api.infra.database.entities;

import gok.api.domain.models.OrderModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "created_at")
    private Date createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderProductEntity> orderProducts;

    public OrderModel toModel() {
        var builder = OrderModel.builder()
                .id(id)
                .totalPrice(totalPrice);

        if (customer != null) {
            customer.setOrders(null);
            builder.customer(customer.toModel());
        }

        if (orderProducts != null) {
            builder.orderProducts(orderProducts.stream().map(orderProductEntity -> {
                orderProductEntity.setOrder(null);
                return orderProductEntity.toModel();
            }).toList());
        }

        return builder.build();
    }
}
