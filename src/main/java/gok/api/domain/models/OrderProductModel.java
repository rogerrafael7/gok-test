package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class OrderProductModel {
    private Long id;
    private Long quantity;
    private BigDecimal unitPrice;
    private ProductModel product;
}

