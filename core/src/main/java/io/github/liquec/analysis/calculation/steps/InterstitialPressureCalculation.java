package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Calculation;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterstitialPressureCalculation implements Calculation {

    private static final Logger LOG = LoggerFactory.getLogger(InterstitialPressureCalculation.class);

    public void execute(final SessionState sessionState, final ResultState resultState) {
        LOG.debug("Calculating interstitial pressure...");

    }

}
