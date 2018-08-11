/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import java.util.Arrays;
import java.util.Collection;

public enum Mode {
    EUROCODE("Eurocode", Arrays.asList(Eurocode.values()), 1.25f),
    NCSE_02("NCSE-02", Arrays.asList(Ncse02.values()), 1.50f);

    private String description;
    private Collection<Enum<? extends Step>> steps;
    private Float safetyFactor;

    Mode(final String description, final Collection<Enum<? extends Step>> steps, final Float safetyFactor) {
        this.description = description;
        this.steps = steps;
        this.safetyFactor = safetyFactor;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Enum<? extends Step>> getSteps() {
        return steps;
    }

    public Float getSafetyFactor() {
        return safetyFactor;
    }

    public static final Mode getMode(final String description) {
        for (Mode mode: Mode.values()) {
            if (mode.getDescription().equals(description)) {
                return mode;
            }
        }
        return null;
    }
}
