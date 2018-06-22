/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.calculation.steps.*;

public enum Eurocode implements Step {

    CHECK_DEPTH_ABOVE_GWT("Check Depth Above GWT", CheckDepthAboveGwtRunnable.class),
    CHECK_MAX_DEPTH("Check Max Depth", CheckMaxDepthRunnable.class),
    CHECK_NO_LIQUEFACTION("Check No Liquefaction", CheckNoLiquefactionRunnable.class),
    CALCULATE_TOTAL_STRESS("Total Stress", TotalStressRunnable.class),
    CALCULATE_INTERSTITIAL_STRESS("Interstitial Stress", InterstitialStressRunnable.class),
    CALCULATE_EFFECTIVE_STRESS("Effective Stress", EffectiveStressRunnable.class),
    CALCULATE_EFFECTIVE_STRESS_FACTOR("Effective Stress Factor", EffectiveStressFactorRunnable.class),
    CALCULATE_SPT_CORRECTION("SPT Correction", SptCorrectionRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO("Cycle Resistance Ratio (CRR)", CycleResistanceRatioRunnable.class),
    CALCULATE_EARTHQUAKE_MAGNITUDE_CORRECTION("Earthquake Magnitude Correction (CM)", EarthquakeMagnitudeCorrectionRunnable.class),
    CALCULATE_CYCLE_RESISTANCE_RATIO_CORRECTION("Cycle Resistance Ratio (CRR) Correction", CycleResistanceRatioCorrectionRunnable.class),
    CALCULATE_CYCLE_STRESS_RATIO("Cycle Stress Ratio (CSR)", CycleStressRatioRunnable.class),
    CALCULATE_SAFETY_FACTOR("Safety Factor", SafetyFactorRunnable.class);

    private String description;
    private Class<? extends Runnable> step;

    Eurocode(final String description, final Class<? extends Runnable> step) {
        this.description = description;
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends Runnable> getStepClass() {
        return step;
    }

}
