package gok.api.application.services;

import gok.api.domain.models.ProductModel;
import gok.api.domain.models.TopCustomerModel;
import gok.api.domain.repositories.CustomerRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import jakarta.inject.Inject;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@QuarkusTest
public class CustomerServiceTest {

    @InjectMock
    private CustomerRepository customerRepository;

    @Inject
    private CustomerService customerService;

    private List<TopCustomerModel> mockTopCustomers;
    private List<ProductModel> mockProductSuggestions;

    @BeforeEach
    void setUp() {
        TopCustomerModel customer1 = TopCustomerModel.builder()
                .id(1)
                .name("João Silva")
                .totalDeCompras(15L)
                .totalGasto(1500.00)
                .build();

        TopCustomerModel customer2 = TopCustomerModel.builder()
                .id(2)
                .name("Maria Souza")
                .totalDeCompras(12L)
                .totalGasto(1200.00)
                .build();

        TopCustomerModel customer3 = TopCustomerModel.builder()
                .id(3)
                .name("Carlos Pereira")
                .totalDeCompras(10L)
                .totalGasto(950.00)
                .build();

        mockTopCustomers = Arrays.asList(customer1, customer2, customer3);

        ProductModel product1 = ProductModel.builder()
                .id(1)
                .name("Vinho Tinto Cabernet")
                .currentPrice(new java.math.BigDecimal("79.90"))
                .build();

        ProductModel product2 = ProductModel.builder()
                .id(2)
                .name("Cerveja IPA")
                .currentPrice(new java.math.BigDecimal("12.90"))
                .build();

        mockProductSuggestions = Arrays.asList(product1, product2);
    }

    @Test
    void testGetTopCustomers() {
        when(customerRepository.getTopCustomers()).thenReturn(mockTopCustomers);

        List<TopCustomerModel> result = customerService.getTopCustomers();

        verify(customerRepository, times(1)).getTopCustomers();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("João Silva", result.get(0).getName());
        assertEquals("Maria Souza", result.get(1).getName());
        assertEquals("Carlos Pereira", result.get(2).getName());
        assertEquals(1500.00, result.get(0).getTotalGasto());
        assertEquals(15, result.get(0).getTotalDeCompras());
    }

    @Test
    void testGetSuggestionProduct() {
        Long customerId = 1L;
        
        when(customerRepository.getSuggestionProduct(customerId)).thenReturn(mockProductSuggestions);

        List<ProductModel> result = customerService.getSuggestionProduct(customerId);

        verify(customerRepository, times(1)).getSuggestionProduct(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Vinho Tinto Cabernet", result.get(0).getName());
        assertEquals("Cerveja IPA", result.get(1).getName());
    }
}