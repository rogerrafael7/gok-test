package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductModel {
    private Long id;
    private String name;
    private BigDecimal price;
    private CategoryModel category;
}