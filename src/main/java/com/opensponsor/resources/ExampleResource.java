package com.opensponsor.resources;

import com.opensponsor.entitys.Example;
import com.opensponsor.payload.PageParams;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.ResultOfPaging;
import com.opensponsor.payload.github.*;
import com.opensponsor.repositorys.ExampleRepository;
import com.opensponsor.utils.GithubClient;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Parameters;
import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import java.util.ArrayList;
import java.util.List;

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

    @Inject
    public ExampleRepository exampleRepository;

    @Inject
    public GithubClient githubClient;

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
//        if(token.error == null) {
//            System.out.println(this.githubClient.listOrganizationRepositories("ghu_rvQxgHJMide5IPNuXEmq89kSsIga311ydtUM"));
//        } else {
//            System.out.println("token is invalidation");
//        }

//        System.out.println(this.githubClient.listOrganizations("ghu_rvQxgHJMide5IPNuXEmq89kSsIga311ydtUM"));

        // scope=user:email,read:org,repo
//        System.out.println(this.githubClient.getAccessToken("getAccessToken").access_token);

        // https://github.com/login/oauth/authorize?client_id=Ov23liRlXfQF98hEmTpa&scope=public_repo%20read:org

        // 二次授权
        // https://github.com/settings/connections/applications/Ov23liRlXfQF98hEmTpa

        /*this.githubClient.listOrgs("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").forEach(it -> {
            System.out.println(it.toString());
        });*/

        String token = "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";

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
            .status(200)
            .entity(new ResultOfData<>(groups))
            .build();
    }
}
