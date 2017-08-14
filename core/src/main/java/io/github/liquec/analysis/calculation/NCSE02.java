/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.calculation.steps.*;

public enum NCSE02 implements Step {

    CHECK_DEPTH_ABOVE_GWT(CheckDepthAboveGwtRunnable.class),
    CALCULATE_TOTAL_STRESS(TotalStressRunnable.class),
    CALCULATE_INTERSTITIAL_PRESSURE(InterstitialPressureRunnable.class),
    CALCULATE_EFFECTIVE_PRESSURE(EffectivePressureRunnable.class),
    CALCULATE_FACTOR_EFFECTIVE_PRESSURE(FactorEffectivePressureRunnable.class),
    CALCULATE_SPT_CORRECTED(SptCorrectedRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO(CycleResistanceRatioRunnable.class),
    CALCULATE_COEFFICIENT_OF_CONTRIBUTION(CoefficientOfContributionRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTED(CycleResistanceRatioCorrectedRunnable.class),
    CALCULATE_FACTOR_DEPTH(FactorDepthRunnable.class),
    CALCULATE_CYCLE_STRESS_RATIO(CycleStressRatioRunnable.class),
    CALCULATE_FACTOR_SAFETY(FactorSafetyRunnable.class);

    private Class<? extends Runnable> step;

    NCSE02(final Class<? extends Runnable> step) {
        this.step = step;
    }

    public Class<? extends Runnable> getStepClass() {
        return step;
    }

}
