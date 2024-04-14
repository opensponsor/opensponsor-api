package com.opensponsor.resources;

import com.opensponsor.entitys.User;
import com.opensponsor.payload.LoginBody;
import com.opensponsor.payload.RegisterBody;
import com.opensponsor.payload.ResultOfObject;
import com.opensponsor.repositorys.SessionRepository;
import com.opensponsor.repositorys.UserRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpStatusClass;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import java.util.ArrayList;

@OpenAPIDefinition(
    info = @Info(title="Session API", version = "1.0.1")
)
@Tag(name="Session", description="Register and login operations")
@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class SessionResource {

    @Inject
    UserRepository userRepository;

    @Inject
    SessionRepository sessionRepository;

    @POST
    @Path("login")
    @Operation(summary = "Update an existing pet")
    @APIResponse(
        responseCode = "200",
        description = "User List",
        content = @Content(
            schema = @Schema(
                implementation = ResultOfObject.class,
                properties = {
                    @SchemaProperty(name = "list", type = SchemaType.ARRAY, implementation = User.class),
                }
            )
        )
    )
    @APIResponse(responseCode = "400", description = "User not found")
    @Transactional
    public Response login(@Valid LoginBody loginBody) {

        return Response
            .status(200)
            .entity(
                ResultOfObject
                    .builder()
                    .size(loginBody)
                    .build()
            )
            .build();
    }

    @POST
    @Path("register")
    @Operation(summary = "User register")
    public Response register(@Valid RegisterBody registerBody) {
        if(sessionRepository.validOfRegister(registerBody)) {
            User user = sessionRepository.createUser(registerBody);
            return Response
                .status(200)
                .entity(user)
                .build();
        } else {
            return Response
                .status(HttpResponseStatus.BAD_REQUEST.code())
                .entity(sessionRepository.getViolationReport())
                .build();
        }
    }
}
