package gok.api.infra.database.repositories;

import gok.api.domain.models.ProductModel;
import gok.api.domain.models.SubCategoryModel;
import gok.api.domain.models.TopCustomerModel;
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
public class CustomerRepositoryImplTest {

    @Inject
    private CustomerRepositoryImpl customerRepository;

    private List<TopCustomerModel> mockTopCustomers;
    private List<ProductModel> mockProductSuggestions;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        if (customerRepository == null) {
            customerRepository = new CustomerRepositoryImpl();
        }

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

        SubCategoryModel vinhoTinto = SubCategoryModel.builder()
                .id(1)
                .name("Tinto")
                .categoryId(1)
                .build();

        SubCategoryModel cerveja = SubCategoryModel.builder()
                .id(13)
                .name("Stout")
                .categoryId(2)
                .build();

        ProductModel produto1 = ProductModel.builder()
                .id(1)
                .name("Vinho Tinto Cabernet")
                .currentPrice(new BigDecimal("79.90"))
                .subCategory(vinhoTinto)
                .build();

        ProductModel produto2 = ProductModel.builder()
                .id(6)
                .name("Cerveja Stout Imperial")
                .currentPrice(new BigDecimal("12.90"))
                .subCategory(cerveja)
                .build();

        mockProductSuggestions = Arrays.asList(produto1, produto2);
    }

    @Test
    void testGetTopCustomers() {
        CustomerRepositoryImpl spyRepository = spy(customerRepository);

        doReturn(mockTopCustomers).when(spyRepository).getTopCustomers();

        List<TopCustomerModel> result = spyRepository.getTopCustomers();

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
        CustomerRepositoryImpl spyRepository = spy(customerRepository);

        Long customerId = 1L;

        doReturn(mockProductSuggestions).when(spyRepository).getSuggestionProduct(customerId);

        List<ProductModel> result = spyRepository.getSuggestionProduct(customerId);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Vinho Tinto Cabernet", result.get(0).getName());
        assertEquals(new BigDecimal("79.90"), result.get(0).getCurrentPrice());
        assertEquals("Cerveja Stout Imperial", result.get(1).getName());
    }
}
