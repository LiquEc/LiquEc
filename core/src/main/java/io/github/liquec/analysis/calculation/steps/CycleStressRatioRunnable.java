package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class CycleStressRatioRunnable extends Runnable {

    public CycleStressRatioRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        Double cycleStressRatio = 0.65 * (sptCalculationResult.getTotalStress() / sptCalculationResult.getEffectiveStress()) * sessionState.getSiteConditions().getPeakGroundAcceleration();

        if (Mode.NCSE_02.equals(this.mode)) {
            cycleStressRatio = cycleStressRatio * sptCalculationResult.getDepthFactor();
        }

        LOG.debug(":::::: Cycle stress ratio (CSR): " + cycleStressRatio);

        sptCalculationResult.setCycleStressRatio(cycleStressRatio);

        this.logEnd();
    }

}
