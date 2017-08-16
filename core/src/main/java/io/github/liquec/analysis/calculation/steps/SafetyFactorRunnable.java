package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class SafetyFactorRunnable extends Runnable {

    public SafetyFactorRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final Double safetyFactor = sptCalculationResult.getCycleResistanceRatioCorrected() / sptCalculationResult.getCycleStressRatio();

        LOG.debug(":::::: Safety factor: " + safetyFactor);

        sptCalculationResult.setSafetyFactor(safetyFactor);

        this.logEnd();
    }

}
