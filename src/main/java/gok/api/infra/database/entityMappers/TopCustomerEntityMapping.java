package gok.api.infra.database.entityMappers;

import gok.api.domain.models.TopCustomerModel;
import jakarta.persistence.*;

@SqlResultSetMapping(
        name = "TopCustomerEntityMapping",
        classes = @ConstructorResult(
                targetClass = TopCustomerModel.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "count_compras", type = Long.class),
                        @ColumnResult(name = "total", type = Double.class),
                }
        )
)
@Entity
public class TopCustomerEntityMapping {
    @Id
    private Integer id;
}
