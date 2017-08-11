/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.calculation.steps.InterstitialPressureCalculation;
import io.github.liquec.analysis.calculation.steps.TotalTensionCalculation;

public enum Eurocode implements Step {

    CALCULATE_TOTAL_TENSION(TotalTensionCalculation.class),
    CALCULATE_INTERSTITIAL_PRESSURE(InterstitialPressureCalculation.class);

    private Class<? extends Calculation> step;

    Eurocode(final Class<? extends Calculation> step) {
        this.step = step;
    }

    public Class<? extends Calculation> getStepClass() {
        return step;
    }

}
