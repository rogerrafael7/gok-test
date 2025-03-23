package gok.api.application.presentation.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import gok.api.domain.dto.HealthCheckResponse;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
public class HealthCheckController {

    @GET
    public HealthCheckResponse healthCheck() {
        return new HealthCheckResponse("Running!");
    }
}
