package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EffectivePressureFactorRunnable extends Runnable {

    public EffectivePressureFactorRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Effective Pressure Factor Mode " + this.mode.getDescription());

        double effectivePressureFactor = Math.pow(100/sptCalculationResult.getEffectivePressure(), 1/2);

        if (mode == Mode.EUROCODE) {
            effectivePressureFactor = effectivePressureFactor < 0.5 ? 0.5 : effectivePressureFactor > 2.0 ? 2.0 : effectivePressureFactor;
        }

        LOG.debug(":::::: Effective pressure factor:" + effectivePressureFactor);

        sptCalculationResult.setEffectivePressureFactor(effectivePressureFactor);

        LOG.debug("::: End Effective Pressure Factor Mode " + this.mode.getDescription());
    }

}
