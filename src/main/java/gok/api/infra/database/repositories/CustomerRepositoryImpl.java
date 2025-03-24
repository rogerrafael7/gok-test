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
    public List<ProductModel> getSuggestionProduct(Long customerId) {
        String query = """
                                WITH produto_mais_comprato as (SELECT p.id           as produto_id,
                                                      p.name         as produto_name,
                                                      p.price        as produto_price,
                                                      sc.id          as sub_categoria_id,
                                                      sc.name        as sub_categoria_name,
                                                      sc.category_id as categoria_id,
                                                      count(p.id)    as count_produto
                                               FROM products p
                                                        inner join public.order_products op on p.id = op.product_id
                                                        inner join public.orders o on o.id = op.order_id
                                                        inner join public.customers c on c.id = o.customer_id
                                                        inner join public.sub_categories sc on sc.id = p.sub_category_id
                                               WHERE c.id = :customerId
                                               group by p.id, sc.id, sc.name, sc.category_id, produto_price
                                               ORDER BY count_produto DESC, produto_price DESC
                                               LIMIT 1)
                SELECT p.id           as produto_id,
                       p.name         as produto_name,
                       p.price        as produto_price,
                       sc.id          as sub_categoria_id,
                       sc.name        as sub_categoria_name,
                       sc.category_id as categoria_id
                FROM products p
                         inner join sub_categories sc on sc.id = p.sub_category_id
                WHERE sc.category_id = (SELECT categoria_id FROM produto_mais_comprato)
                ORDER BY random()
                LIMIT 1;
                """;

        return em.createNativeQuery(query, TopCategoryOrderedMapping.class.getSimpleName())
                .setParameter("customerId", customerId)
                .getResultList();
    }
}
