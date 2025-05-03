package com.opensponsor.repositorys;

import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.Tier;
import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import com.opensponsor.utils.GenerateViolationReport;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class TierRepository extends RepositoryBase<Tier> {
    @Transactional
    public Tier create(Tier tier) {
        tier.organization = Organization.findById(tier.organization.id);
        tier.persistAndFlush();

        return tier;
    }

    public boolean validOfData(Tier tier) {
        String sql = "organization = :organization AND name = :name";
        Map<String, Object> params = new HashMap<>(Map.of(
            "organization", tier.organization,
            "name", tier.name
        ));
        if (tier.id != null) {
            sql += " AND id = :id";
            params.put("id", tier.id);
        }
        long count = Tier.find(sql, params).count();

        if(count == 0) {
            return true;
        } else {
            this.generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "name", "名称已经存在", tier.name
                )
            );
            this.violationReport = this.generateViolationReport.build();
            return false;
        }
    }

}
