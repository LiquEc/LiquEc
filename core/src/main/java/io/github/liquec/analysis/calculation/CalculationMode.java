/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import java.util.Arrays;
import java.util.Collection;

public enum CalculationMode {
    EUROCODE("Eurocode", Arrays.asList(Eurocode.values())),
    NCSE_02("NCSE-02", Arrays.asList(NCSE02.values()));

    private String mode;
    private Collection<Enum<? extends Step>> steps;

    CalculationMode(final String mode, final Collection<Enum<? extends Step>> steps) {
        this.mode = mode;
        this.steps = steps;
    }

    public String getMode() {
        return mode;
    }

    public Collection<Enum<? extends Step>> getSteps() {
        return steps;
    }
}
