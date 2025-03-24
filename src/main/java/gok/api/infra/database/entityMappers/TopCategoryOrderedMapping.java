package gok.api.infra.database.entityMappers;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.SqlResultSetMapping;

@SqlResultSetMapping(
        name = "TopCategoryOrderedMapping",
        columns = {

        }
)
@Entity
public class TopCategoryOrderedMapping {
    @Id
    Long id;
}
