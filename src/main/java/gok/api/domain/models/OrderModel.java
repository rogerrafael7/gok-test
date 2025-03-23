package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderModel {
    private Long id;
    private BigDecimal totalPrice;
    private CustomerModel customer;
    private List<OrderProductModel> orderProducts;
}
