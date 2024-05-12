package com.opensponsor.repositorys;

import com.opensponsor.entitys.User;
import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {
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
}
