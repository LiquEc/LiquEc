package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SptCorrectedRunnable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(SptCorrectedRunnable.class);

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("Calculating SPT corrected...");

    }

}
