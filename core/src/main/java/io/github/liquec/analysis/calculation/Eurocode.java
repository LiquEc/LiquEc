/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.calculation.steps.*;

public enum Eurocode implements Step {

    CHECK_DEPTH_ABOVE_GWT(CheckDepthAboveGwtRunnable.class),
    CHECK_MAX_DEPTH(CheckMaxDepthRunnable.class),
    CHECK_NO_LIQUEFACTION(CheckNoLiquefactionRunnable.class),
    CALCULATE_TOTAL_STRESS(TotalStressRunnable.class),
    CALCULATE_INTERSTITIAL_PRESSURE(InterstitialPressureRunnable.class),
    CALCULATE_EFFECTIVE_PRESSURE(EffectivePressureRunnable.class),
    CALCULATE_EFFECTIVE_PRESSURE_FACTOR(EffectivePressureFactorRunnable.class),
    CALCULATE_SPT_CORRECTION(SptCorrectionRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO(CycleResistanceRatioRunnable.class),
    CALCULATE_EARTHQUAKE_MAGNITUDE(EarthquakeMagnitudeRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION(CycleResistanceRatioCorrectionRunnable.class),
    CALCULATE_CYCLE_STRESS_RATIO(CycleStressRatioRunnable.class),
    CALCULATE_SAFETY_FACTOR(SafetyFactorRunnable.class);

    private Class<? extends Runnable> step;

    Eurocode(final Class<? extends Runnable> step) {
        this.step = step;
    }

    public Class<? extends Runnable> getStepClass() {
        return step;
    }

}
