package gok.api.infra.database.repositories;

import gok.api.domain.models.OrderModel;
import gok.api.domain.repositories.OrderRepository;
import gok.api.infra.database.entities.OrderEntity;
import gok.api.infra.shared.exceptions.SERVER_EXCEPTION_CAUSE;
import gok.api.infra.shared.exceptions.ServerException;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class OrderRepositoryImpl extends OrderRepository implements PanacheRepository<OrderEntity> {

    @Override
    public PaginationResponse<OrderModel> getOrders(PaginationRequest paginationRequest) {
        List<OrderEntity> entities = find("ORDER BY totalPrice")
                .page(paginationRequest.getPage() - 1, paginationRequest.getLimit())
                .list();

        List<OrderModel> orders = entities.stream()
                .map(OrderEntity::toModel)
                .collect(Collectors.toList());

        long totalOrders = count();
        int totalPages = (int) Math.ceil((double) totalOrders / paginationRequest.getLimit());

        return new PaginationResponse<>(
                paginationRequest.getPage(),
                paginationRequest.getLimit(),
                totalOrders,
                totalPages,
                paginationRequest.getPage() < totalPages,
                paginationRequest.getPage() > 1,
                orders
        );
    }

    @Override
    public OrderModel getBiggestOrderByYear(int year) {
        var params = Parameters.with("year", year);
        OrderEntity orderEntity = find("EXTRACT(YEAR FROM createdAt) = :year ORDER BY totalPrice DESC", params)
                .firstResult();

        if (orderEntity == null) {
            throw new ServerException("No orders found for the year " + year, SERVER_EXCEPTION_CAUSE.NOT_FOUND);
        }

        return orderEntity.toModel();
    }
}
