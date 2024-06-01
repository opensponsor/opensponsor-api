package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.Tier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class TierRepository extends RepositoryBase<Tier> {
    @Inject
    UserRepository userRepository;

    @Transactional
    public Tier create(Tier tier) {
        tier.organization = Organization.findById(tier.organization.id);
        tier.persistAndFlush();
        return tier;
    }
}
