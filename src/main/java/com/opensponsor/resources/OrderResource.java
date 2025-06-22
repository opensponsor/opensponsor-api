package com.opensponsor.resources;

import com.opensponsor.entitys.Order;
import com.opensponsor.payload.PageParams;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.ResultOfPaging;
import com.opensponsor.repositorys.OrderRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@OpenAPIDefinition(
    tags = {
        @Tag(name="Order", description="query order")
    },
    info = @Info(
        title="Order API",
        version = "1.0.1")
)
@Path("/order")
@RolesAllowed({"User"})
public class OrderResource {
    @Inject
    OrderRepository orderRepository;

    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response list(@BeanParam Order params, @BeanParam PageParams pageParams) throws IOException {
        return Response
            .status(200)
            .entity(new ResultOfPaging<>(orderRepository.filter(params), PageParams.of(pageParams)))
            .build();
    }

    @GET()
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detail(@PathParam("id") String id) {
        Order order = Order.findById(UUID.fromString(id));

        return Response
            .status(order != null ? Response.Status.OK : Response.Status.NOT_FOUND)
            .entity(new ResultOfData<>(order))
            .build();
    }

}
