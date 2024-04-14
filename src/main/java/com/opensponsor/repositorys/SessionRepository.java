package com.opensponsor.repositorys;


import com.opensponsor.entitys.User;
import com.opensponsor.payload.RegisterBody;
import com.opensponsor.utils.GenerateViolationReport;
import com.opensponsor.utils.SecurityTools;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import java.util.ArrayList;
import java.util.UUID;

@ApplicationScoped
public class SessionRepository implements PanacheRepositoryBase<User, UUID> {
    private ViolationReport violationReport;

    @Inject
    SecurityTools securityTools;

    @Transactional
    public User createUser(RegisterBody registerBody) {

        User user = new User();
        user.name = registerBody.name;
        user.legalName = registerBody.legalName;
        user.sex = registerBody.sex;
        user.password = securityTools.generatePassword(registerBody.password);

        user.persistAndFlush();

        ViolationReport violationReport = new ViolationReport();
        ArrayList<ResteasyConstraintViolation> list = new ArrayList<>(){{
            add(new ResteasyConstraintViolation());
        }};
        violationReport.setPropertyViolations(list);

        return user;
    }

    public boolean validOfRegister(RegisterBody registerBody) {
        long count = User.find("name", registerBody.name).count();
        if(count == 0) {
            return true;
        } else {
            GenerateViolationReport generateViolationReport = new GenerateViolationReport();
            generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "name", "用户名已经存在", registerBody.name
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
