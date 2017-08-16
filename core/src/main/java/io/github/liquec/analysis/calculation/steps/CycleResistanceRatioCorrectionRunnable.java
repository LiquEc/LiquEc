package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class CycleResistanceRatioCorrectionRunnable extends Runnable {

    public CycleResistanceRatioCorrectionRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        Double cycleResistanceRatioCorrection;

        if (Mode.EUROCODE.equals(this.mode)) {
            cycleResistanceRatioCorrection = sptCalculationResult.getCycleResistanceRatio() * sptCalculationResult.getEarthquakeMagnitudeCorrection();
        } else {
            cycleResistanceRatioCorrection = sptCalculationResult.getCycleResistanceRatio() * sptCalculationResult.getCoefficientContributionCorrection();
        }

        LOG.debug(":::::: Cycle resistance ratio correction: " + cycleResistanceRatioCorrection);

        sptCalculationResult.setCycleResistanceRatioCorrected(cycleResistanceRatioCorrection);

        this.logEnd();
    }

}
