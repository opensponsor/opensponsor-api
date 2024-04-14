package com.opensponsor.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserUniqueValidator implements ConstraintValidator<Unique, String> {
    @Override
    public void initialize(Unique unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        // Session s =  sessionFactory.getCurrentSession();
        // s.createQuery("s", User.class);
        // Context currentContext = Vertx.currentContext();
        // currentContext.runOnContext( event -> {
        //     System.out.println(
        //         User.find("name", name).count()
        //     );
        // });

        return false;
    }
}
