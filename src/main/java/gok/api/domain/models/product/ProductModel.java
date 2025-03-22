package gok.api.domain.models.product;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProductModel {
    private Long id;
    private String name;
    private BigDecimal price;
}