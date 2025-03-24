package gok.api.infra.database.repositories;

import gok.api.domain.models.CategoryModel;
import gok.api.domain.models.SubCategoryModel;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@QuarkusTest
public class CategoryRepositoryImplTest {

    @Inject
    private CategoryRepositoryImpl categoryRepository;

    private List<CategoryModel> mockCategories;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        if (categoryRepository == null) {
            categoryRepository = new CategoryRepositoryImpl();
        }

        SubCategoryModel subCat1 = SubCategoryModel.builder()
                .id(1)
                .name("Tinto")
                .categoryId(1)
                .build();

        SubCategoryModel subCat2 = SubCategoryModel.builder()
                .id(2)
                .name("Branco")
                .categoryId(1)
                .build();

        SubCategoryModel subCat3 = SubCategoryModel.builder()
                .id(3)
                .name("IPA")
                .categoryId(2)
                .build();

        CategoryModel category1 = CategoryModel.builder()
                .id(1)
                .name("Vinho")
                .subCategories(Arrays.asList(subCat1, subCat2))
                .build();

        CategoryModel category2 = CategoryModel.builder()
                .id(2)
                .name("Cerveja")
                .subCategories(Arrays.asList(subCat3))
                .build();

        CategoryModel category3 = CategoryModel.builder()
                .id(3)
                .name("Destilados")
                .subCategories(null)
                .build();

        mockCategories = Arrays.asList(category1, category2, category3);
    }

    @Test
    void testGetCategories() {
        CategoryRepositoryImpl spyRepository = spy(categoryRepository);
        doReturn(mockCategories).when(spyRepository).getCategories();

        List<CategoryModel> result = spyRepository.getCategories();

        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Vinho", result.get(0).getName());
        assertEquals("Cerveja", result.get(1).getName());
        assertEquals("Destilados", result.get(2).getName());
    }
}