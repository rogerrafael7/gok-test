package gok.api.infra.database.repositories;

import gok.api.domain.models.CustomerModel;
import gok.api.domain.models.OrderModel;
import gok.api.domain.models.OrderProductModel;
import gok.api.domain.models.ProductModel;
import gok.api.infra.shared.types.DefaultPaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@QuarkusTest
public class OrderRepositoryImplTest {

    @Inject
    private OrderRepositoryImpl orderRepository;

    private List<OrderModel> mockOrders;
    private OrderModel mockBiggestOrder;
    private PaginationResponse<OrderModel> mockPaginationResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        if (orderRepository == null) {
            orderRepository = new OrderRepositoryImpl();
        }

        CustomerModel customer1 = CustomerModel.builder()
                .id(1)
                .name("João Silva")
                .build();

        CustomerModel customer2 = CustomerModel.builder()
                .id(2)
                .name("Maria Souza")
                .build();

        ProductModel produto1 = ProductModel.builder()
                .id(1)
                .name("Vinho Tinto Cabernet")
                .currentPrice(new BigDecimal("79.90"))
                .build();

        ProductModel produto2 = ProductModel.builder()
                .id(2)
                .name("Cerveja IPA")
                .currentPrice(new BigDecimal("12.90"))
                .build();

        OrderProductModel orderProduct1 = OrderProductModel.builder()
                .id(1)
                .quantity(2)
                .unitPrice(new BigDecimal("79.90"))
                .product(produto1)
                .build();

        OrderProductModel orderProduct2 = OrderProductModel.builder()
                .id(2)
                .quantity(6)
                .unitPrice(new BigDecimal("12.90"))
                .product(produto2)
                .build();

        OrderModel order1 = OrderModel.builder()
                .id(1)
                .customer(customer1)
                .totalPrice(new BigDecimal("159.80"))
                .orderProducts(Arrays.asList(orderProduct1))
                .build();

        OrderModel order2 = OrderModel.builder()
                .id(2)
                .customer(customer2)
                .totalPrice(new BigDecimal("77.40"))
                .orderProducts(Arrays.asList(orderProduct2))
                .build();

        OrderModel order3 = OrderModel.builder()
                .id(3)
                .customer(customer1)
                .totalPrice(new BigDecimal("237.20"))
                .orderProducts(Arrays.asList(orderProduct1, orderProduct2))
                .build();

        mockOrders = Arrays.asList(order1, order2, order3);
        mockBiggestOrder = order3;

        mockPaginationResponse = new PaginationResponse<>(
                1,
                10,
                mockOrders.size(),
                1,
                false,
                false,
                mockOrders
        );
    }

    @Test
    void testGetOrders() {
        OrderRepositoryImpl spyRepository = spy(orderRepository);

        DefaultPaginationRequest paginationRequest = new DefaultPaginationRequest();
        paginationRequest.setPage(1);
        paginationRequest.setLimit(10);

        doReturn(mockPaginationResponse).when(spyRepository).getOrders(paginationRequest);

        PaginationResponse<OrderModel> result = spyRepository.getOrders(paginationRequest);

        assertNotNull(result);
        assertEquals(3, result.data().size());
        assertEquals(1, result.page());
        assertEquals(10, result.limit());
        assertEquals(3, result.totalItems());
        assertEquals(1, result.totalPages());

        OrderModel firstOrder = result.data().get(0);
        assertEquals(1, firstOrder.getId());
        assertEquals("João Silva", firstOrder.getCustomer().getName());
        assertEquals(1, firstOrder.getOrderProducts().size());
        assertEquals(new BigDecimal("159.80"), firstOrder.getTotalPrice());
    }

    @Test
    void testGetBiggestOrderByYear() {
        OrderRepositoryImpl spyRepository = spy(orderRepository);

        int year = 2023;

        doReturn(mockBiggestOrder).when(spyRepository).getBiggestOrderByYear(year);

        OrderModel result = spyRepository.getBiggestOrderByYear(year);

        assertNotNull(result);
        assertEquals(3, result.getId());
        assertEquals("João Silva", result.getCustomer().getName());
        assertEquals(2, result.getOrderProducts().size());
        assertEquals(new BigDecimal("237.20"), result.getTotalPrice());
    }
}