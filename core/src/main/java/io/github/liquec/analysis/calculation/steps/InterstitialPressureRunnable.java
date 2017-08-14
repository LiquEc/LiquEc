package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Constant;
import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class InterstitialPressureRunnable extends Runnable {

    public InterstitialPressureRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Interstitial Pressure Mode " + this.mode.getDescription());

        final double interstitialPressure = Constant.WATER_DENSITY.getValue(mode) * (sptCalculationResult.getDepth() - sessionState.getGeotechnicalProperties().getGroundWaterTableDepth());

        LOG.debug(":::::: Interstitial pressure:" + interstitialPressure + " KN/m2");

        sptCalculationResult.setInterstitialPressure(interstitialPressure);

        LOG.debug("::: End Interstitial Pressure Mode " + this.mode.getDescription());
    }

}
