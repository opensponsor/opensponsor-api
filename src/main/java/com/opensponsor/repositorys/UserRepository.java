package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.User;
import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import com.opensponsor.payload.UpdateUserPassword;
import com.opensponsor.utils.SecurityTools;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;

import java.util.*;

@ApplicationScoped
public class UserRepository extends RepositoryBase<User> {
    @Inject
    SecurityContext ctx;

    @Inject
    SecurityTools securityTools;

    @Inject
    SessionRepository sessionRepository;

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

    public User authUser(boolean fullData) {
        User user = null;
        if(ctx.getUserPrincipal() != null) {
            user = User.find("username", ctx.getUserPrincipal().getName()).firstResult();
            if(fullData) {
                user.setGetFullData(true);
            }
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

    public boolean updatePassword(UpdateUserPassword data) {
        User user = this.authUser(true);
        user.password = securityTools.generatePassword(data.password);
        if(!sessionRepository.verifySmsCode(user.countryCode, user.phoneNumber, data.code)) {
            generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "smsCode", "验证码不正确或已经过期", data.code
                )
            );
            this.violationReport = generateViolationReport.build();
            return false;
        }
        user.persistAndFlush();

        return true;
    }
}
