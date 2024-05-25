package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;

import java.util.UUID;

@ApplicationScoped
public class TierRepository implements PanacheRepositoryBase<Tier, UUID> {
    @Inject
    UserRepository userRepository;

    public boolean checkOwnership(Tier tier) {
        User user = userRepository.authUser();
        if(tier.organization != null && tier.organizationId != null) {
            Organization organization = Organization.findById(tier.organizationId);
            return user.id == organization.user.id;
        }
        return false;
    }

    public Tier create(Tier tier) {
        tier.organization = Organization.findById(tier.organizationId);
        tier.persistAndFlush();
        return tier;
    }
}
