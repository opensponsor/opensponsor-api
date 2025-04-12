package com.opensponsor.repositorys;

import com.opensponsor.entitys.Tags;
import com.opensponsor.entitys.Tier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class TagsRepository extends RepositoryBase<Tier> {
    @Transactional
    public Set<Tags> createAll(Set<Tags> tagsList) {
        Set<Tags> tags = new HashSet<>();
        tagsList.forEach(t -> {
            Optional<Tags> f = Tags.find("name = ?1", t.name).firstResultOptional();
            if(f.isPresent()) {
                tags.add(f.get());
            } else {
                t.persist();
                tags.add(t);
            }
        });
        return tags;
    }
}
