package gok.api.infra.database.repositories;

import gok.api.domain.models.ProductModel;
import gok.api.domain.models.TopCustomerModel;
import gok.api.domain.repositories.CustomerRepository;
import gok.api.infra.database.entities.CustomerEntity;
import gok.api.infra.database.entityMappers.TopCategoryOrderedMapping;
import gok.api.infra.database.entityMappers.TopCustomerEntityMapping;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class CustomerRepositoryImpl extends CustomerRepository implements PanacheRepository<CustomerEntity> {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<TopCustomerModel> getTopCustomers() {
        String sql = """
                    WITH maiores_compras as (
                        SELECT c.id, count(op.id) as count_compras, sum(o.total_price) as total
                        FROM orders o
                        INNER JOIN customers c ON c.id = o.customer_id
                        INNER JOIN order_products op ON o.id = op.order_id
                        GROUP BY c.id, c.name
                        ORDER BY total DESC
                    )
                    SELECT c.id, c.name, mc.count_compras, mc.total FROM customers c
                    JOIN maiores_compras mc ON c.id = mc.id
                    ORDER BY mc.total DESC, mc.count_compras DESC
                    LIMIT 3
                """;

        return em.createNativeQuery(sql, TopCustomerEntityMapping.class.getSimpleName()).getResultList();
    }

    @Override
    public List<ProductModel> getSuggestionProduct(Long customerId, Long categoryId) {
//        List<TopCategoryOrderedMapping> topCategoryOrderedMappings = em.createNativeQuery("""
//                SELECT ca.name as categoria, count(ca.id) as count_categoria
//                FROM orders o
//                         INNER JOIN customers c ON c.id = o.customer_id
//                         INNER JOIN order_products op ON o.id = op.order_id
//                         INNER JOIN products p ON p.id = op.product_id
//                         INNER JOIN categories ca ON ca.id = p.category_id
//                WHERE c.id = :customerId AND p.id != 1
//                group by ca.id
//                order by count_categoria DESC
//                LIMIT 1;
//                """, TopCategoryOrderedMapping.class.getSimpleName())
//                .setParameter("customerId", customerId)
//                .getResultList();
        return List.of();
    }
}
