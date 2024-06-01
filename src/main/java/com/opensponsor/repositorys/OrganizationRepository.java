package com.opensponsor.repositorys;


import com.opensponsor.entitys.Organization;
import com.opensponsor.entitys.User;
import com.opensponsor.utils.GenerateViolationReport;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

@ApplicationScoped
public class OrganizationRepository extends RepositoryBase<Organization> {
    private ViolationReport violationReport;

    @Inject
    protected UserRepository userRepository;

    @Transactional
    public Organization create(Organization organization) {
        organization.user = userRepository.authUser();
        organization.persistAndFlush();
        return organization;
    }

    public PanacheQuery<Organization> getAll() {
        User user = userRepository.authUser();
        return this.find("user", user);
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
