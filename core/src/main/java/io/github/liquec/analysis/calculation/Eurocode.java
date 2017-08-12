/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.calculation.steps.*;

public enum Eurocode implements Step {

    CALCULATE_TOTAL_TENSION(TotalTensionRunnable.class),
    CALCULATE_INTERSTITIAL_PRESSURE(InterstitialPressureRunnable.class),
    CALCULATE_EFFECTIVE_PRESSURE(EffectivePressureRunnable.class),
    CALCULATE_SPT_CORRECTED(SptCorrectedRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO(CycleResistanceRatioRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTED(CycleResistanceRatioCorrectedRunnable.class),
    CALCULATE_CYCLE_STRESS_RATIO(CycleStressRatioRunnable.class),
    CALCULATE_FACTOR_SAFETY(FactorSafetyRunnable.class);

    private Class<? extends Runnable> step;

    Eurocode(final Class<? extends Runnable> step) {
        this.step = step;
    }

    public Class<? extends Runnable> getStepClass() {
        return step;
    }

}
