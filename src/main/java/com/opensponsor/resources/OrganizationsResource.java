package com.opensponsor.resources;

import com.opensponsor.entitys.Organization;
import com.opensponsor.payload.ResultOfPaging;
import com.opensponsor.repositorys.OrganizationRepository;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.quarkus.panache.common.Page;
import io.quarkus.security.Authenticated;
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

@OpenAPIDefinition(
    tags = {
        @Tag(name="widget", description="Widget operations."),
        @Tag(name="gasket", description="Operations related to gaskets")
    },
    info = @Info(
        title="Example API",
        version = "1.0.1",
        contact = @Contact(
            name = "Example API Support",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Path("/organizations")
@Authenticated
public class OrganizationsResource {

    @Inject
    OrganizationRepository organizationRepository;

    @Tag(name = "Hello", description = "Operations related to gaskets")
    @Operation(summary = "Update an existing pet")
    @APIResponse(
        responseCode = "200",
        description = "User List",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = Organization.class
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
    public Response create(@Valid Organization organization) {
        if(organizationRepository.validOfData(organization)) {
            return Response
                .status(HttpResponseStatus.OK.code())
                .entity(organizationRepository.create(organization))
                .build();
        } else {
            return Response
                .status(HttpResponseStatus.BAD_REQUEST.code())
                .entity(organizationRepository.getViolationReport())
                .build();
        }
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid Organization organization) {
        if(organizationRepository.checkOwnership(organization.user)) {
            Organization exist = Organization.findById(organization.id);
            if(!exist.name.equals(organization.name)) {
                return Response
                    .status(HttpResponseStatus.FORBIDDEN.code())
                    .build();
            } else {
                return Response
                    .status(HttpResponseStatus.OK.code())
                    .entity(organizationRepository.save(organization))
                    .build();
            }
        } else {
            return Response
                .status(HttpResponseStatus.FORBIDDEN.code())
                .entity(organizationRepository.getViolationReport())
                .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response
            .status(HttpResponseStatus.OK.code())
            .entity(new ResultOfPaging<>(organizationRepository.getAll(), new Page(0, 10)))
            .build();
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response detail(@PathParam("name") String name) {
        return Response
            .status(HttpResponseStatus.OK.code())
            .entity(organizationRepository.find("name", name).firstResult())
            .build();
    }
}
