package com.opensponsor.utils;

import com.opensponsor.payload.github.GithubAccessToken;
import com.opensponsor.payload.github.GithubOrg;
import com.opensponsor.payload.github.GithubRepo;
import com.opensponsor.payload.github.GithubUser;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class GithubClient {
    private final String clientId = System.getenv("githubAppClientId");
    private final String clientSecret = System.getenv("githubAppClientSecret");

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final Gson gson = new Gson();

    /**
     * {
     *     "access_token": "ghu_NGjt9pBpxeaThFvH9FMAVO84LjOLcy3m30nj",
     *     "refresh_token_expires_in": "15724800",
     *     "refresh_token": "ghr_rFrbQLfupyZZ6RwcaroTx9hI75l96RvHLWXNaKIuUmxPp3h67e0Qz3j3zboP9EyNrp1t0h03XpHV",
     *     "scope": "",
     *     "token_type": "bearer",
     *     "expires_in": "28800"
     * }
     * @param code string
     * @return GithubAccessToken
     * @throws IOException github error
     */
    public GithubAccessToken getAccessToken(String code) throws IOException {
        String url = String.format(
            "https://github.com/login/oauth/access_token?client_id=%s&client_secret=%s&code=%s",
            clientId,
            clientSecret,
            code
        );
        HttpPost request = new HttpPost(url);
        request.addHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        String res = new String(entity.getContent().readAllBytes());
        return gson.fromJson(res, GithubAccessToken.class);
    }

    public GithubUser getAuthUser(String token) throws IOException {
        HttpGet request = new HttpGet("https://api.github.com/user");
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("X-GitHub-Api-Version", "2022-11-28");
        request.addHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        String res = new String(entity.getContent().readAllBytes());

        return gson.fromJson(res, GithubUser.class);
    }

    /**
     * 获取登录用户的repos
     * @param token string
     * @return json string
     * @throws IOException github error
     */
    public List<GithubRepo> listUserRepos(String token) throws IOException {
        HttpGet request = new HttpGet("https://api.github.com/user/repos?per_page=100");
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("X-GitHub-Api-Version", "2022-11-28");
        request.addHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        List<GithubRepo> repos = new ArrayList<>();
        String res = new String(entity.getContent().readAllBytes());
        List<?> list = gson.fromJson(res, List.class);
        list.forEach(re -> repos.add(gson.fromJson(gson.toJson(re), GithubRepo.class)));

        return repos;
    }

    /**
     * 获取登录用户的org list
     * @param token string
     * @return json string
     * @throws IOException github error
     */
    public List<GithubOrg> listUserOrgs(String token) throws IOException {
        HttpGet request = new HttpGet("https://api.github.com/user/orgs?per_page=100");
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("X-GitHub-Api-Version", "2022-11-28");
        request.addHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        List<GithubOrg> repos = new ArrayList<>();
        String res = new String(entity.getContent().readAllBytes());
        List<?> list = gson.fromJson(res, List.class);
        list.forEach(re -> repos.add(gson.fromJson(gson.toJson(re), GithubOrg.class)));

        return repos;
    }

    /**
     * 获取组织下公开的仓库
     *
     * @param org string
     * @param token string
     * @return string
     * @throws IOException github error
     */
    public List<GithubRepo> listOrgRepos(String org, String token) throws IOException {
        String url = String.format("https://api.github.com/orgs/%S/repos?per_page=100", org);
        HttpGet request = new HttpGet(url);
        request.addHeader("Authorization", "Bearer " + token);
        request.addHeader("X-GitHub-Api-Version", "2022-11-28");
        request.addHeader("Accept", "application/json");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        List<GithubRepo> repos = new ArrayList<>();
        String res = new String(entity.getContent().readAllBytes());
        List<?> list = gson.fromJson(res, List.class);
        list.forEach(re -> repos.add(gson.fromJson(gson.toJson(re), GithubRepo.class)));

        return repos;
    }
}
