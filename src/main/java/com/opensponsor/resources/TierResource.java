package com.opensponsor.resources;

import com.opensponsor.entitys.Tier;
import com.opensponsor.repositorys.TierRepository;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.api.validation.ViolationReport;

@OpenAPIDefinition(
    tags = {
        @Tag(name="widget", description="sponsor tier."),
    },
    info = @Info(
        title="Tier API",
        version = "1.0.1",
        contact = @Contact(
            name = "Tier CURD Support",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Path("/tier")
@RolesAllowed({ "User" })
public class TierResource {
    @Inject
    TierRepository tierRepository;

    @Operation(summary = "Create sponsor tiers")
    @APIResponse(
        responseCode = "200",
        description = "create tier",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = Tier.class
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "User not found",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ViolationReport.class
            )
        )
    )
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid Tier tier) {
        if(tierRepository.checkOwnership(tier)) {
            return Response.ok(tierRepository.create(tier)).build();
        } else {
            return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Update sponsor tiers")
    @APIResponse(
        responseCode = "200",
        description = "Update tier",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = Tier.class
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "Tier not found",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ViolationReport.class
            )
        )
    )
    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid Tier tier) {
        if(tierRepository.checkOwnership(tier)) {
            tier.persistAndFlush();
            return Response.ok(tier).build();
        } else {
            return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
    }

    @Operation(summary = "Delete sponsor tiers")
    @APIResponse(
        responseCode = "200",
        description = "Delete tier",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = Boolean.class
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "Tier not found",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ViolationReport.class
            )
        )
    )
    @DELETE
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@Valid Tier tier) {
        if(tierRepository.checkOwnership(tier)) {
            tierRepository.delete(tier);
            return Response.ok(true).build();
        } else {
            return Response.status(HttpStatus.SC_UNAUTHORIZED).build();
        }
    }
}
