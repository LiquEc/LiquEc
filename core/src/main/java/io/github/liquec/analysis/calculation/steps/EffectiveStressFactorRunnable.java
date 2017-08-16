package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EffectiveStressFactorRunnable extends Runnable {

    public EffectiveStressFactorRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        double effectiveStressFactor = Math.pow(100.0 / sptCalculationResult.getEffectiveStress(), 0.5);

        if (Mode.EUROCODE.equals(this.mode)) {
            effectiveStressFactor = effectiveStressFactor < 0.5 ? 0.5 : effectiveStressFactor > 2.0 ? 2.0 : effectiveStressFactor;
        }

        LOG.debug(":::::: Effective stress factor: " + effectiveStressFactor);

        sptCalculationResult.setEffectiveStressFactor(effectiveStressFactor);

        this.logEnd();
    }

}
