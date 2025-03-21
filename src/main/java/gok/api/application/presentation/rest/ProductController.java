package gok.api.application.presentation.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import gok.api.domain.dto.CreateProductRequest;
import gok.api.domain.dto.GetProductsByIdsRequest;
import gok.api.domain.dto.GetProductsByIdsResponse;
import gok.api.domain.dto.UpdateProductRequest;
import gok.api.domain.models.product.ProductModel;
import gok.api.domain.services.ProductService;
import gok.api.infra.shared.types.DefaultPaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;

import java.util.UUID;

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
    @Path("/by-ids")
    public GetProductsByIdsResponse getProductsByIds(
            @BeanParam
            GetProductsByIdsRequest request
    ) {
        var ids = request.getProductIdsAsList();
        if (ids == null || ids.isEmpty()) {
            throw new BadRequestException("productIds query parameter is required");
        }
        return productService.getProductsByIds(ids);
    }

    @GET
    @Path("/{id}")
    public ProductModel getProductById(@PathParam("id") UUID id) {
        return productService.getProductById(id);
    }

    @POST
    public ProductModel createProduct(CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PATCH
    @Path("/{id}")
    public void updateProduct(@PathParam("id") UUID id, UpdateProductRequest request) {
        productService.updateProduct(id, request);
    }

    @DELETE
    @Path("/{id}")
    public void deleteProduct(@PathParam("id") UUID id) {
        productService.deleteProduct(id);
    }
}
