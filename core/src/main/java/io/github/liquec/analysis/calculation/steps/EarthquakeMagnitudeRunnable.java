package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class EarthquakeMagnitudeRunnable extends Runnable {

    public EarthquakeMagnitudeRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Earthquake Magnitude Mode " + this.mode.getDescription());

        LOG.debug("::: End Earthquake Magnitude Mode " + this.mode.getDescription());
    }

}
