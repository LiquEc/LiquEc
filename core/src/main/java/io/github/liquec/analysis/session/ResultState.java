/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.session;

import io.github.liquec.analysis.model.CalculationModeEnum;

public class ResultState {

    private CalculationModeEnum calculationModeEnum;

    public ResultState(final CalculationModeEnum calculationModeEnum) {
        this.calculationModeEnum = calculationModeEnum;
    }

    public CalculationModeEnum getCalculationModeEnum() {
        return calculationModeEnum;
    }

    public void setCalculationModeEnum(final CalculationModeEnum calculationModeEnum) {
        this.calculationModeEnum = calculationModeEnum;
    }
}
