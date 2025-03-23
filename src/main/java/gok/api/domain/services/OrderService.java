package gok.api.domain.services;

import gok.api.domain.models.OrderModel;
import gok.api.domain.repositories.OrderRepository;
import gok.api.infra.shared.types.PaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderService {
    @Inject
    OrderRepository orderRepository;

    public PaginationResponse<OrderModel> getOrders(PaginationRequest paginationRequest) {
        return orderRepository.getOrders(paginationRequest);
    }

    public OrderModel getBiggestOrderByYear(int year) {
        return orderRepository.getBiggestOrderByYear(year);
    }
}
