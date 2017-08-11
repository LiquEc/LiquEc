/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.session;

public class ResultState {

    private String calculationMode;

    public ResultState() {}

    public ResultState(final String calculationMode) {
        this.calculationMode = calculationMode;
    }

    public String getCalculationMode() {
        return calculationMode;
    }

    public void setCalculationMode(final String calculationMode) {
        this.calculationMode = calculationMode;
    }
}
