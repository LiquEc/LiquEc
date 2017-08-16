package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EffectiveStressRunnable extends Runnable {

    public EffectiveStressRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final double effectiveStress = sptCalculationResult.getTotalStress() - sptCalculationResult.getInterstitialStress();

        LOG.debug(":::::: Effective stress: " + effectiveStress + " KN/m2");

        sptCalculationResult.setEffectiveStress(effectiveStress);

        this.logEnd();
    }

}
