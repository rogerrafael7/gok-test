package gok.api.application.presentation.rest;

import gok.api.domain.models.OrderModel;
import gok.api.application.services.OrderService;
import gok.api.infra.shared.types.DefaultPaginationRequest;
import gok.api.infra.shared.types.PaginationResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/compras")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    OrderService orderService;

    @GET
    public PaginationResponse<OrderModel> getOrders(@BeanParam
                                                    DefaultPaginationRequest paginationParams) {
        return orderService.getOrders(paginationParams);
    }

    @GET
    @Path("/maior-compra/{year}")
    public OrderModel getBiggestOrderByYear(@PathParam("year") int year) throws Exception {
        return orderService.getBiggestOrderByYear(year);
    }

}
