package gok.api.domain.models.productcategory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProductCategoryModel {
    private Long id;
    private Long productId;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 