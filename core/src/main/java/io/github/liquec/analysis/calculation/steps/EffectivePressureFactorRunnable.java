package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EffectivePressureFactorRunnable extends Runnable {

    public EffectivePressureFactorRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        double effectivePressureFactor = Math.pow(100 / sptCalculationResult.getEffectivePressure(), 1/2);

        if (mode == Mode.EUROCODE) {
            effectivePressureFactor = effectivePressureFactor < 0.5 ? 0.5 : effectivePressureFactor > 2.0 ? 2.0 : effectivePressureFactor;
        }

        LOG.debug(":::::: Effective pressure factor: " + effectivePressureFactor);

        sptCalculationResult.setEffectivePressureFactor(effectivePressureFactor);

        this.logEnd();
    }

}
