package com.opensponsor.resources;

import com.opensponsor.constants.ResponseSchemaType;
import com.opensponsor.entitys.Licenses;
import com.opensponsor.payload.ResultOfArray;
import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
    tags = {
        @Tag(name="widget", description="licenses list"),
    },
    info = @Info(
        title="licenses API",
        version = "1.0.1",
        contact = @Contact(
            name = "Licenses List",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Path("/licenses")
public class LicensesResource {

    @Operation(summary = "list licenses")
    @APIResponse(
        responseCode = "200",
        description = "list licenses",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfArray.class,
                properties = {
                    @SchemaProperty(name = ResponseSchemaType.RECORDS, type = SchemaType.ARRAY, implementation = Licenses.class),
                }
            )
        )
    )
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.status(HttpResponseStatus.OK.code())
            .entity(new ResultOfArray<>(Licenses.findAll().list()))
            .build();
    }
}
