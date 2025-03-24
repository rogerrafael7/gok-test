package gok.api.domain.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SubCategoryModel {
    private Integer id;
    private String name;
    private Integer categoryId;
}