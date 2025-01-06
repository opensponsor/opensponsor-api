package com.opensponsor.payload.github;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema()
public class GithubAccessToken {
    @Schema(required = true, type = SchemaType.STRING, description = "access token")
    public String access_token;

    public String refresh_token_expires_in;
    public String refresh_token;
    public String scope;
    public String token_type;
    public String expires_in;

    public String error;
    public String error_description;
    public String error_uri;
}
