package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class TierRepository implements PanacheRepositoryBase<Tier, UUID> {
    @Inject
    UserRepository userRepository;

    public boolean checkOwnership(Tier tier) {
        User user = userRepository.authUser();
        Organization organization = Organization.findById(tier.organization.id);
        return user.id == organization.user.id;
    }

    public Tier create(Tier tier) {
        tier.organization = Organization.findById(tier.organization.id);
        tier.persistAndFlush();
        return tier;
    }
}
