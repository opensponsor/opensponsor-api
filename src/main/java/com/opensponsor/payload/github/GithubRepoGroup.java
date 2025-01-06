package com.opensponsor.payload.github;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema()
public class GithubRepoGroup {
    @Schema(required = true, type = SchemaType.STRING, description = "login name")
    public String login;

    @Schema(required = true, type = SchemaType.STRING, description = "owner type")
    public String type;

    @Schema(description = "org info")
    public GithubOrg org;

    @Schema(description = "user info")
    public GithubUser user;

    @Schema(required = true, type = SchemaType.ARRAY, description = "user info")
    public List<GithubRepo> repos;

    public GithubRepoGroup(String login, String type, GithubUser user, List<GithubRepo> repos) {
        this.login = login;
        this.type = type;
        this.user = user;
        this.repos = repos;
    }

    public GithubRepoGroup(String login, String type, GithubOrg org, List<GithubRepo> repos) {
        this.login = login;
        this.type = type;
        this.org = org;
        this.repos = repos;
    }
}
