package com.opensponsor.repositorys;


import com.opensponsor.entitys.CountryCode;
import com.opensponsor.entitys.SmsCode;
import com.opensponsor.entitys.User;
import com.opensponsor.payload.LoginBody;
import com.opensponsor.payload.RegisterBody;
import com.opensponsor.utils.GenerateViolationReport;
import com.opensponsor.utils.SecurityTools;
import com.opensponsor.utils.TokenTools;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.api.validation.ConstraintType;
import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SessionRepository implements PanacheRepositoryBase<User, UUID> {
    @Inject
    UserRepository userRepository;

    private ViolationReport violationReport;

    @Inject
    SecurityTools securityTools;
    @Inject
    TokenTools tokenTools;

    @Transactional
    public User create(RegisterBody registerBody) {
        User user = new User();
        user.username = registerBody.username;
        user.phoneNumber = registerBody.phoneNumber;
        user.slug = registerBody.slug;
        user.countryCode = CountryCode.findById(registerBody.countryCode.id);
        user.sex = registerBody.sex;
        user.password = securityTools.generatePassword(registerBody.password);

        user.persistAndFlush();
        // 同时创建一个 organization
        this.userRepository.transformToOrganizationAndPersist(user).persistAndFlush();

        ViolationReport violationReport = new ViolationReport();
        ArrayList<ResteasyConstraintViolation> list = new ArrayList<>(){{
            add(new ResteasyConstraintViolation());
        }};
        violationReport.setPropertyViolations(list);

        return user;
    }

    public boolean validOfRegister(RegisterBody registerBody) {
        long count = User.find("slug", registerBody.slug).count();
        GenerateViolationReport generateViolationReport = new GenerateViolationReport();

        if(count > 0) {
            generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "slug", "url 已经存在", registerBody.slug
                )
            );
            this.violationReport = generateViolationReport.build();
            return false;
        }

        long phoneCount = User.find("phoneNumber", registerBody.phoneNumber).count();
        if(phoneCount > 0) {
            generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "phoneNumber", "手机号已经注册，请登录", registerBody.phoneNumber
                )
            );
            this.violationReport = generateViolationReport.build();
            return false;
        }

        if(!this.verifySmsCode(registerBody.countryCode, registerBody.phoneNumber, registerBody.code)) {
            generateViolationReport.add(
                new ResteasyConstraintViolation(
                    ConstraintType.Type.PARAMETER, "smsCode", "验证码不正确或已经过期", registerBody.code
                )
            );
            this.violationReport = generateViolationReport.build();
            return false;
        }

        return true;
    }

    @Transactional
    public User loginForPhoneNumber(LoginBody loginBody) {
        Optional<User> user
            = User.find("phoneNumber = ?1 and countryCode = ?2", loginBody.phoneNumber, loginBody.countryCode).firstResultOptional();
        if(user.isPresent()) {
            if(securityTools.matches(loginBody.password, user.get().password)) {
                User authUser = user.get();
                tokenTools.generate(authUser, List.of("User"));
                return authUser;
            }
        }
        return null;
    }

    @Transactional
    public User loginForEmail(LoginBody loginBody) {
        Optional<User> user = User.find("email", loginBody.email).firstResultOptional();
        if(user.isPresent()) {
            if(securityTools.matches(loginBody.password, user.get().password)) {
                User authUser = user.get();
                tokenTools.generate(authUser, List.of("User"));
                return authUser;
            }
        }
        return null;
    }

    private boolean verifySmsCode(CountryCode countryCode, String phoneNumber, String code) {
        CountryCode country = CountryCode.findById(countryCode.id);
        Optional<SmsCode> smsCode = SmsCode
            .find("countryCode = ?1 and phoneNumber = ?2 and code = ?3 and effective = true", country,  phoneNumber, code)
            .firstResultOptional();
        Instant now = Instant.now();

        return smsCode.isPresent() && smsCode.get().whenCreated.getEpochSecond() - now.getEpochSecond() < 120;
    }

    @Transactional
    public User login(LoginBody loginBody) {
        Optional<User> user = User.find("username", loginBody.phoneNumber).firstResultOptional();
        Optional<SmsCode> code = SmsCode
            .find("phoneNumber = ?1 and code = ?2 and effective = true", loginBody.phoneNumber, loginBody.code)
            .firstResultOptional();

        Instant now = Instant.now();
        if(user.isPresent()) {
            if(code.isPresent() && code.get().whenCreated.getEpochSecond() - now.getEpochSecond() < 120) {
                User authUser = user.get();
                tokenTools.generate(authUser, List.of("User"));

                SmsCode smsCode = code.get();
                smsCode.effective = false;
                smsCode.persistAndFlush();
                return authUser;
            } else {
                return null;
            }
        } else if(code.isPresent() && code.get().whenCreated.getEpochSecond() - now.getEpochSecond() < 120) {
            // create new user
            User newUser = new User();
            newUser.phoneNumber = loginBody.phoneNumber;
            newUser.username = loginBody.phoneNumber;
            newUser.persistAndFlush();
            tokenTools.generate(newUser, List.of("User"));

            SmsCode smsCode = code.get();
            smsCode.effective = false;
            smsCode.persistAndFlush();
            return newUser;
        } else {
            return null;
        }
    }

    public ViolationReport getViolationReport() {
        return violationReport;
    }
}
