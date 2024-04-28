package com.opensponsor.resources;

import com.opensponsor.entitys.User;
import com.opensponsor.entitys.UserToken;
import com.opensponsor.enums.E_SEX;
import com.opensponsor.payload.LoginBody;
import com.opensponsor.payload.RegisterBody;
import com.opensponsor.repositorys.SessionRepository;
import com.opensponsor.repositorys.UserRepository;
import com.opensponsor.utils.GenerateViolationReport;
import com.opensponsor.utils.TokenTools;
import io.netty.handler.codec.http.HttpResponseStatus;
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
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import java.util.List;

@OpenAPIDefinition(
    info = @Info(title="Session API", version = "1.0.1")
)
@Tag(name="Session", description="Register and login operations")
@Path("/session")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class SessionResource {
    @Inject
    GenerateViolationReport generateViolationReport;

    @Inject
    TokenTools tokenTools;

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
                implementation = LoginBody.class,
                properties = {
                    @SchemaProperty(name = "list", type = SchemaType.ARRAY, implementation = User.class),
                }
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "User not found",
        content = @Content(
            schema = @Schema(
                implementation = ViolationReport.class
            )
        )
    )
    public Response login(@Valid LoginBody loginBody) {
        User user = sessionRepository.login(loginBody);
        if(user != null) {
            return Response
                .status(HttpResponseStatus.OK.code())
                .entity(user)
                .build();
        } else {
            return Response
                .status(HttpResponseStatus.BAD_REQUEST.code())
                .entity(generateViolationReport.exception("用户不存在").build())
                .build();
        }
    }

    @POST
    @Path("register")
    @Operation(summary = "User register")
    public Response register(@Valid RegisterBody registerBody) {
        if(sessionRepository.validOfRegister(registerBody)) {
            User user = sessionRepository.createUser(registerBody);
            return Response
                .status(HttpResponseStatus.OK.code())
                .entity(user)
                .build();
        } else {
            return Response
                .status(HttpResponseStatus.BAD_REQUEST.code())
                .entity(sessionRepository.getViolationReport())
                .build();
        }
    }

    @GET
    @Path("test")
    @Transactional
    public Response test() {
        User user2 = User.find("name", "!!").firstResult();
        System.out.println(user2.token);

        return Response.status(200).entity(user2).build();
    }
}
