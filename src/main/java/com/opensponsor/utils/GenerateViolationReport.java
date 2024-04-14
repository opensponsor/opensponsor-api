package com.opensponsor.utils;

import org.jboss.resteasy.api.validation.ResteasyConstraintViolation;
import org.jboss.resteasy.api.validation.ViolationReport;

import java.util.ArrayList;

public class GenerateViolationReport {
    private final ArrayList<ResteasyConstraintViolation> violations = new ArrayList<>();

    public GenerateViolationReport add(ResteasyConstraintViolation resteasyConstraintViolation) {
        violations.add(resteasyConstraintViolation);
        return this;
    }

    public ViolationReport build() {
        ViolationReport violationReport = new ViolationReport();
        violationReport.setPropertyViolations(this.violations);

        return violationReport;
    }
}
