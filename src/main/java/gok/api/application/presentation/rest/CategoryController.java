package gok.api.application.presentation.rest;

import gok.api.domain.models.CategoryModel;
import gok.api.application.services.CategoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryService categoryService;

    @GET
    public List<CategoryModel> getCategories() {
        return categoryService.getCategories();
    }

}
