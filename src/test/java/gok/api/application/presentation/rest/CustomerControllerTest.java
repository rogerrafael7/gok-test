package gok.api.application.presentation.rest;

import gok.api.application.services.CustomerService;
import gok.api.domain.models.ProductModel;
import gok.api.domain.models.TopCustomerModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class CustomerControllerTest {

    @InjectMock
    CustomerService customerService;

    @Inject
    CustomerController customerController;

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

        ProductModel produto1 = ProductModel.builder()
                .id(1)
                .name("Vinho Tinto Cabernet")
                .currentPrice(new BigDecimal("79.90"))
                .build();

        ProductModel produto2 = ProductModel.builder()
                .id(6)
                .name("Cerveja Stout Imperial")
                .currentPrice(new BigDecimal("12.90"))
                .build();

        mockProductSuggestions = Arrays.asList(produto1, produto2);
    }

    @Test
    void testGetTopCustomers() {
        when(customerService.getTopCustomers()).thenReturn(mockTopCustomers);

        given()
            .when().get("/clientes/top")
            .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].name", is("João Silva"))
                .body("[1].name", is("Maria Souza"))
                .body("[2].name", is("Carlos Pereira"))
                .body("[0].totalGasto", is(1500.0F))
                .body("[0].totalDeCompras", is(15));

        List<TopCustomerModel> result = customerController.getTopCustomers();
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

        when(customerService.getSuggestionProduct(customerId)).thenReturn(mockProductSuggestions);

        given()
            .when().get("/clientes/recomendacao/client/1/tipo")
            .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].name", is("Vinho Tinto Cabernet"))
                .body("[1].name", is("Cerveja Stout Imperial"));

        List<ProductModel> result = customerController.getSuggestionProduct(customerId);
        assertEquals(2, result.size());
        assertEquals("Vinho Tinto Cabernet", result.get(0).getName());
        assertEquals("Cerveja Stout Imperial", result.get(1).getName());
    }
}