package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderProductModel {
    private Integer id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductModel product;
}

