package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Constant;
import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class InterstitialStressRunnable extends Runnable {

    public InterstitialStressRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final double interstitialStress = 10.0 * (sptCalculationResult.getDepth() - sessionState.getGeotechnicalProperties().getGroundWaterTableDepth());

        LOG.debug(":::::: Interstitial stress: " + interstitialStress + " KN/m2");

        sptCalculationResult.setInterstitialStress(interstitialStress);

        this.logEnd();
    }

}
