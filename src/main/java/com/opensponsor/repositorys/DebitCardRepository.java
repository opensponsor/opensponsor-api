package com.opensponsor.repositorys;

import com.opensponsor.entitys.DebitCard;
import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.Tier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DebitCardRepository extends RepositoryBase<DebitCard> {
    @Inject
    UserRepository userRepository;

    @Transactional
    public DebitCard create(DebitCard debitCard) {
        debitCard.organization = Organization.findById(debitCard.organization.id);
        debitCard.persistAndFlush();
        return debitCard;
    }
}
