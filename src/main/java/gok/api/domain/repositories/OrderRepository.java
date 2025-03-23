package gok.api.domain.repositories;

import gok.api.domain.models.OrderModel;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

public abstract class OrderRepository {
    public abstract PaginationResponse<OrderModel> getOrders(PaginationRequest paginationRequest);
    public abstract OrderModel getBiggestOrderByYear(int year);
}
