package gok.api.application.presentation.rest;

import gok.api.domain.models.product.ProductModel;
import gok.api.domain.services.ProductService;
import gok.api.infra.shared.types.DefaultPaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {
    @Inject
    ProductService productService;

    @GET
    public PaginationResponse<ProductModel> getProducts(
            @BeanParam
            DefaultPaginationRequest paginationParams
    ) {
        return productService.getProducts(paginationParams);
    }

    @GET
    @Path("/{id}")
    public ProductModel getProductById(@PathParam("id") Long id) {
        return productService.getProductById(id);
    }
}
