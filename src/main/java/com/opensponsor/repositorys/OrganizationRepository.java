package com.opensponsor.repositorys;


import com.opensponsor.entitys.Organization;
import com.opensponsor.utils.GenerateViolationReport;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import java.util.UUID;

@ApplicationScoped
public class OrganizationRepository extends RepositoryBase<Organization> {
    private ViolationReport violationReport;

    @Transactional
    public Organization create(Organization organization) {
        organization.persistAndFlush();
        return organization;
    }

    public PanacheQuery<Organization> filter(Organization params) {
        PanacheQuery<Organization> query = Organization.findAll();
        if(params.userId != null) {
            query.filter("organization(=userId)", Parameters.with("userId", UUID.fromString(params.userId)));
        }

        return query;
    }

    public boolean validOfData(Organization organization) {
        long count = Organization.find("name", organization.name).count();
        if(count == 0) {
            return true;
        } else {
            GenerateViolationReport generateViolationReport = new GenerateViolationReport();
            generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "name", "名称已经存在", organization.name
                )
            );
            this.violationReport = generateViolationReport.build();
            return false;
        }
    }

    public ViolationReport getViolationReport() {
        return violationReport;
    }
}
