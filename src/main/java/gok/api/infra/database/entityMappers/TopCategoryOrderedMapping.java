package gok.api.infra.database.entityMappers;

import gok.api.domain.models.ProductModel;
import gok.api.domain.models.SubCategoryModel;
import jakarta.persistence.*;

import java.math.BigDecimal;

@SqlResultSetMapping(
        name = "TopCategoryOrderedMapping",
        classes = @ConstructorResult(
                targetClass = ProductModel.class,
                columns = {
                        @ColumnResult(name = "produto_id", type = Integer.class),
                        @ColumnResult(name = "produto_name", type = String.class),
                        @ColumnResult(name = "produto_price", type = BigDecimal.class),
                        @ColumnResult(name = "sub_categoria_id", type = Integer.class),
                        @ColumnResult(name = "sub_categoria_name", type = String.class),
                        @ColumnResult(name = "categoria_id", type = Integer.class),
                })
)
@Entity
public class TopCategoryOrderedMapping {
    @Id
    Integer id;
}
