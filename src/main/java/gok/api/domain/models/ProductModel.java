package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ProductModel {
    private Integer id;
    private String name;
    private BigDecimal currentPrice;
    private SubCategoryModel subCategory;

    public ProductModel(Integer id, String name, BigDecimal currentPrice,
                         Integer subCategoryId, String subCategoryName, Integer categoryId) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.subCategory = SubCategoryModel.builder()
                .id(subCategoryId)
                .name(subCategoryName)
                .categoryId(categoryId)
                .build();
    }

    public ProductModel() {
    }

    public ProductModel(Integer id, String name, BigDecimal currentPrice, SubCategoryModel subCategory) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.subCategory = subCategory;
    }
}