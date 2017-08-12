/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TotalTensionRunnable implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(TotalTensionRunnable.class);

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("Calculating total tension...");

    }
}
