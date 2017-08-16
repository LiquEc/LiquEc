package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Polynomial;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EarthquakeMagnitudeCorrectionRunnable extends Runnable {

    public EarthquakeMagnitudeCorrectionRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final Double earthquakeMagnitudeCorrection = Polynomial.EARTHQUAKE_MAGNITUDE_CORRECTION.getValue(sessionState.getSiteConditions().getEarthquakeMagnitude());

        LOG.debug(":::::: Earthquake magnitude correction (CM): " + earthquakeMagnitudeCorrection);

        sptCalculationResult.setEarthquakeMagnitudeCorrection(earthquakeMagnitudeCorrection);

        this.logEnd();
    }

}
