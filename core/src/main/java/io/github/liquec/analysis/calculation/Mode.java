/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import java.util.Arrays;
import java.util.Collection;

public enum Mode {
    EUROCODE("Eurocode", Arrays.asList(Eurocode.values())),
    NCSE_02("NCSE-02", Arrays.asList(Ncse02.values()));

    private String description;
    private Collection<Enum<? extends Step>> steps;

    Mode(final String description, final Collection<Enum<? extends Step>> steps) {
        this.description = description;
        this.steps = steps;
    }

    public String getDescription() {
        return description;
    }

    public Collection<Enum<? extends Step>> getSteps() {
        return steps;
    }
}
