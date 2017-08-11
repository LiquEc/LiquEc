/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum CalculationMode {
    EUROCODE("Eurocode", Eurocode.values()),
    NCSE_02("NCSE-02", NCSE02.values());

    private String mode;
    private Step[] steps;

    CalculationMode(final String mode, final Step[] steps) {
        this.mode = mode;
        this.steps = steps;
    }

    public String getMode() {
        return mode;
    }

    public Step[] getSteps() {
        return steps;
    }
}
