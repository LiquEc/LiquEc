package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EffectivePressureRunnable extends Runnable {

    public EffectivePressureRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final double effectivePressure = sptCalculationResult.getTotalStress()  - sptCalculationResult.getInterstitialPressure();

        LOG.debug(":::::: Effective pressure: " + effectivePressure + " KN/m2");

        sptCalculationResult.setEffectivePressure(effectivePressure);

        this.logEnd();
    }

}
