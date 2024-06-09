package com.opensponsor.resources;

import com.opensponsor.entitys.DebitCard;
import com.opensponsor.entitys.Organization;
import com.opensponsor.repositorys.DebitCardRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

import java.util.UUID;

@OpenAPIDefinition(
    tags = {
        @Tag(name="widget", description="sponsor tier."),
    },
    info = @Info(
        title="DebitCard API",
        version = "1.0.1",
        contact = @Contact(
            name = "DebitCard CURD Support",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Path("/debit-card")
@RolesAllowed({ "User" })
public class DebitCardResource {
    @Inject
    DebitCardRepository debitCardRepository;

    @Operation(summary = "Create sponsor debitCard")
    @APIResponse(
        responseCode = "200",
        description = "create debitCard",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = DebitCard.class
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
    public Response create(@Valid DebitCard debitCard) {
        Organization org = Organization.findById(debitCard.organization.id);
        if(org.debitCard == null) {
            if(debitCardRepository.checkOwnership(debitCard.organization.user)) {
                return Response.ok(debitCardRepository.create(debitCard)).build();
            } else {
                return Response.status(HttpResponseStatus.FORBIDDEN.code()).build();
            }
        } else {
            debitCard.id = org.debitCard.id;
            return this.update(debitCard);
        }
    }

    @Operation(summary = "Update sponsor debitCard")
    @APIResponse(
        responseCode = "200",
        description = "Update debitCard",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = DebitCard.class
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "DebitCard not found",
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
    public Response update(@Valid DebitCard debitCard) {
        if(debitCardRepository.checkOwnership(debitCard.organization.user)) {
            return Response.ok(debitCardRepository.save(debitCard)).build();
        } else {
            return Response.status(HttpResponseStatus.FORBIDDEN.code()).build();
        }
    }
}
