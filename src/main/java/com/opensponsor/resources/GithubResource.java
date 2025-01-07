package com.opensponsor.resources;

import com.opensponsor.constants.ResponseSchemaType;
import com.opensponsor.payload.ResultOfArray;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.github.GithubAccessToken;
import com.opensponsor.payload.github.GithubRepo;
import com.opensponsor.payload.github.GithubRepoGroup;
import com.opensponsor.payload.github.GithubUser;
import com.opensponsor.utils.GithubClient;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@OpenAPIDefinition(
    tags = {
        @Tag(name="widget", description="Github operations."),
    },
    info = @Info(
        title="Github API",
        version = "1.0.1",
        contact = @Contact(
            name = "Github API Support",
            url = "http://exampleurl.com/contact",
            email = "techsupport@example.com"),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
@Path("/github")
@RolesAllowed({ "User" })
public class GithubResource {

    @Inject
    public GithubClient githubClient;

    @Tag(name = "Github", description = "Github Actions")
    @Operation(summary = "get github repository list group by organization")
    @APIResponse(
        responseCode = "200",
        description = "GithubRepoGroup List",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfArray.class,
                properties = {
                    @SchemaProperty(name = ResponseSchemaType.RECORDS, type = SchemaType.ARRAY, implementation = GithubRepoGroup.class),
                }
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "Github API Error"
    )
    @GET
    @Path(value = "list-repo-group")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@QueryParam("token") String token) throws IOException {
        List<GithubRepoGroup> groups = new ArrayList<>();
        GithubUser gu = this.githubClient.getAuthUser(token);
        List<GithubRepo> reposForUser = this.githubClient.listUserRepos(token);
        groups.add(new GithubRepoGroup(gu.login, "user", gu, reposForUser));

        this.githubClient.listUserOrgs(token).forEach(org -> {
            try {
                List<GithubRepo> repos = githubClient.listOrgRepos(org.login, token);
                groups.add(new GithubRepoGroup(org.login, "org", org, repos));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return Response
            .status(HttpStatus.SC_OK)
            .entity(new ResultOfData<>(groups))
            .build();
    }

    @Tag(name = "Github", description = "Github Actions")
    @Operation(summary = "get github accessToken usage code")
    @APIResponse(
        responseCode = "200",
        description = "github accessToken",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = ResponseSchemaType.DATA, type = SchemaType.OBJECT, implementation = GithubAccessToken.class),
                }
            )
        )
    )
    @APIResponse(
        responseCode = "400",
        description = "Github API Error"
    )
    @GET
    @Path(value = "access-token")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccessToken(@QueryParam("code") String code) throws IOException {
        return Response.status(HttpStatus.SC_OK)
            .entity(this.githubClient.getAccessToken(code))
            .build();
    }
}
