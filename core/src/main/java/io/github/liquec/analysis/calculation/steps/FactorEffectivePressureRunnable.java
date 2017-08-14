package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class FactorEffectivePressureRunnable extends Runnable {

    public FactorEffectivePressureRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Factor Effective Pressure Mode " + this.mode.getDescription());

        double factorEffectivePressure = Math.pow(100/sptCalculationResult.getEffectivePressure(), 1/2);

        if (mode == Mode.EUROCODE) {
            factorEffectivePressure = factorEffectivePressure < 0.5 ? 0.5 : factorEffectivePressure > 2.0 ? 2.0 : factorEffectivePressure;
        }

        LOG.debug(":::::: Factor effective pressure:" + factorEffectivePressure);

        sptCalculationResult.setFactorEffectivePressure(factorEffectivePressure);

        LOG.debug("::: End Factor Effective Pressure Mode " + this.mode.getDescription());
    }

}
