package com.opensponsor.resources;

import com.opensponsor.entitys.Tags;
import com.opensponsor.payload.ResultOfArray;
import com.opensponsor.payload.ResultOfPaging;
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

import java.util.List;

@Path("/tags")
public class TagsResource {

    @Tag(name = "Tags", description = "get official tags")
    @Operation(summary = "Get tags")
    @APIResponse(
        responseCode = "200",
        description = "get successful",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfPaging.class,
                properties = {
                    @SchemaProperty(name = "records", type = SchemaType.ARRAY, implementation = Tags.class),
                }
            )
        )
    )
    @GET()
    @Path("official")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOfficialTags() {
        List<Tags> tags = Tags.find("official = true").list();

        return Response
            .status(Response.Status.OK)
            .entity(new ResultOfArray<>(tags))
            .build();
    }

    @Tag(name = "Tags", description = "get popular tags")
    @Operation(summary = "Get tags")
    @APIResponse(
        responseCode = "200",
        description = "get successful",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfPaging.class,
                properties = {
                    @SchemaProperty(name = "records", type = SchemaType.ARRAY, implementation = Tags.class),
                }
            )
        )
    )
    @GET()
    @Path("popular")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPopularTags() {
        List<Tags> tags = Tags.find("popular = true").list();

        return Response
            .status(Response.Status.OK)
            .entity(new ResultOfArray<>(tags))
            .build();
    }

}
