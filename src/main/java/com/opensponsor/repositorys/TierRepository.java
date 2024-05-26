package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.UUID;

@ApplicationScoped
public class TierRepository implements PanacheRepositoryBase<Tier, UUID> {
    @Inject
    UserRepository userRepository;

    public boolean checkOwnership(Tier tier) {
        User user = userRepository.authUser();
        System.out.println(tier.organization);
        System.out.println(tier.organization.id);
        if(tier.organization != null) {
            Organization organization = Organization.findById(tier.organization.id);
            return user.id == organization.user.id;
        }
        return false;
    }

    public Tier create(Tier tier) {
        tier.organization = Organization.findById(tier.organization.id);
        tier.persistAndFlush();
        return tier;
    }

    public void save(Tier tier) {
        EntityManager em = this.getEntityManager();

        if(tier.isPersistent()){
            tier.persist();
        } else {
            em.merge(tier);
        }
    }
}
