package gok.api.application.presentation.rest;

import gok.api.application.services.CategoryService;
import gok.api.domain.models.CategoryModel;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
public class CategoryControllerTest {

    @InjectMock
    CategoryService categoryService;

    @Inject
    CategoryController categoryController;

    private List<CategoryModel> mockCategories;

    @BeforeEach
    void setUp() {
        CategoryModel vinho = CategoryModel.builder()
                .id(1)
                .name("Vinho")
                .build();

        CategoryModel cerveja = CategoryModel.builder()
                .id(2)
                .name("Cerveja")
                .build();

        CategoryModel destilados = CategoryModel.builder()
                .id(3)
                .name("Destilados")
                .build();

        mockCategories = Arrays.asList(vinho, cerveja, destilados);
    }

    @Test
    void testGetCategories() {
        when(categoryService.getCategories()).thenReturn(mockCategories);

        given()
            .when().get("/categorias")
            .then()
                .statusCode(200)
                .body("size()", is(3))
                .body("[0].name", is("Vinho"))
                .body("[1].name", is("Cerveja"))
                .body("[2].name", is("Destilados"));

        List<CategoryModel> result = categoryController.getCategories();
        assertEquals(3, result.size());
        assertEquals("Vinho", result.get(0).getName());
        assertEquals("Cerveja", result.get(1).getName());
        assertEquals("Destilados", result.get(2).getName());
    }
}