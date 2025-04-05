package com.opensponsor.resources;

import com.opensponsor.constants.ResultMessage;
import com.opensponsor.entitys.User;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.UpdateUser;
import com.opensponsor.repositorys.UserRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Optional;

@Path("/user")
public class UserResource {

    @Inject
    protected UserRepository userRepository;

    @Tag(name = "User", description = "get User entity")
    @Operation(summary = "Get User")
    @APIResponse(
        responseCode = "200",
        description = "get successful",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "data", type = SchemaType.OBJECT, implementation = User.class),
                }
            )
        )
    )
    @APIResponse(
        responseCode = "404",
        description = "User Not Found"
    )
    @GET()
    @Path("{slug}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("slug") String slug) {
        Optional<User> user = User.find("slug = ?1", slug).firstResultOptional();

        if(user.isPresent()) {
            return Response
                .status(Response.Status.OK)
                .entity(user.get())
                .build();
        } else {
            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(null)
                .build();
        }
    }

    @Tag(name = "User", description = "update User entity")
    @Operation(summary = "Update User")
    @APIResponse(
        responseCode = "200",
        description = "update successful",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "data", type = SchemaType.OBJECT, implementation = User.class),
                }
            )
        )
    )
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"User"})
    public Response update(@Valid UpdateUser data) {
        User user = this.userRepository.authUser();
        user.username = data.username;
        user.sex = data.sex;
        user.slug = data.slug;
        user.website = data.website;

        user.persistAndFlush();
        return Response
            .status(Response.Status.OK)
            .entity(new ResultOfData<>(data).message(ResultMessage.UPDATE_SUCCESS))
            .build();
    }
}
