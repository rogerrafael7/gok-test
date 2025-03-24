package gok.api.application.presentation.rest;

import gok.api.domain.models.TopCustomerModel;
import gok.api.domain.models.ProductModel;
import gok.api.application.services.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController {

    @Inject
    CustomerService customerService;

    @GET
    @Path("/top")
    public List<TopCustomerModel> getTopCustomers() {
        return customerService.getTopCustomers();
    }

    @GET
    @Path("/recomendacao/client/{customerId}/tipo/{categoryId}")
    public List<ProductModel> getSuggestionProduct(
            @PathParam("customerId") Long customerId,
            @PathParam("categoryId") Long categoryId
    ) {
        return customerService.getSuggestionProduct(customerId, categoryId);
    }
}
