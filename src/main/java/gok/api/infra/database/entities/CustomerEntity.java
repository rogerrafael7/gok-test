package gok.api.infra.database.entities;

import gok.api.domain.models.CustomerModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "customers")
@Getter
@Setter
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "tax_id")
    private String taxId;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<OrderEntity> orders;

    public CustomerModel toModel() {
        var builder = CustomerModel.builder()
                .id(id)
                .name(name)
                .taxId(taxId);

        if (orders != null) {
            builder.orders(orders.stream().map(OrderEntity::toModel).toList());
        }

        return builder.build();
    }
}