package com.opensponsor.resources;

import com.opensponsor.entitys.CountryCode;
import com.opensponsor.entitys.Example;
import com.opensponsor.entitys.SmsCode;
import com.opensponsor.payload.PageParams;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.github.*;
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
import org.jboss.resteasy.api.validation.ViolationReport;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

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
@Path("/example")
public class ExampleResource {

    @Tag(name = "Hello", description = "Operations related to gaskets")
    @Operation(summary = "Update an existing pet")
    @APIResponse(
        responseCode = "200",
        description = "Example List",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "records", type = SchemaType.ARRAY, implementation = GithubRepoGroup.class),
                }
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
    @GET()
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@BeanParam Example params, @BeanParam PageParams pageParams) throws IOException {


        CountryCode cc = CountryCode.findAll().firstResult();
        System.out.println(cc.countryCode);

        Optional<SmsCode> smsCode = SmsCode
            .find("countryCode = ?1 and phoneNumber = ?2 and code = ?3 and effective = true", cc,  "16631132230", "1318")
            .firstResultOptional();


        return Response
            .status(200)
            .entity("-")
            .build();
    }
}
