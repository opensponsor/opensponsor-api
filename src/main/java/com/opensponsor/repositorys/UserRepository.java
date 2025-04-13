package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.User;
import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

import java.util.*;

@ApplicationScoped
public class UserRepository extends RepositoryBase<User> {
    @Inject
    SecurityContext ctx;

    public List<User> list(Page page, Sort sort) {
        return this.findAll(sort)
            .page(page)
            .list();
    }

    public User findByName(String name) {
        return this.find("name", name).firstResult();
    }

    public User authUser() {
        User user = null;
        if(ctx.getUserPrincipal() != null) {
            user = User.find("username", ctx.getUserPrincipal().getName()).firstResult();
        }
        return user;
    }

    public Organization transformToOrganizationAndPersist(User user) {
        Map<String, Object> queryParams = Map.of(
            "user", user,
            "type", E_ORGANIZATION_TYPE.USER
        );
        Organization org = Organization.find("user = :user and type = :type", queryParams).firstResult();
        if(org == null) {
            org = new Organization();
        }
        org.name = user.username;
        org.slug = user.slug;
        org.website = user.website;
        org.social = user.social;
        org.type = E_ORGANIZATION_TYPE.USER;
        org.user = user;

        return org;
    }
}
