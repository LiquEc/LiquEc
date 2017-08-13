/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class TotalTensionRunnable extends Runnable {

    public TotalTensionRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Total Tension Mode " + this.mode.getDescription());

        if (sptCalculationResult.getDepth() < sessionState.getGeotechnicalProperties().getGroundWaterTableDepth()) {
            throw new LiquEcException("");
        }

        LOG.debug("::: End Total Tension Mode " + this.mode.getDescription());
    }
}
