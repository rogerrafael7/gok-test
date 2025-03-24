package gok.api.application.services;

import gok.api.domain.models.CategoryModel;
import gok.api.domain.repositories.CategoryRepository;
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
public class CategoryServiceTest {

    @InjectMock
    private CategoryRepository categoryRepository;

    @Inject
    private CategoryService categoryService;

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
        when(categoryRepository.getCategories()).thenReturn(mockCategories);

        List<CategoryModel> result = categoryService.getCategories();

        verify(categoryRepository, times(1)).getCategories();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Vinho", result.get(0).getName());
        assertEquals("Cerveja", result.get(1).getName());
        assertEquals("Destilados", result.get(2).getName());
    }
}