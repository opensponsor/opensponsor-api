package com.opensponsor;

import com.opensponsor.models.FiscalHost;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/hello")
public class GreetingResource {

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        return Response
            .status(200)
            .entity(FiscalHost.findAll().stream().toList())
            .build();
    }
}
