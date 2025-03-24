package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CategoryModel {
    private Integer id;
    private String name;
    private List<SubCategoryModel> subCategories;
}