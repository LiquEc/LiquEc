package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class FactorDepthRunnable extends Runnable {

    public FactorDepthRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Factor Depth Mode " + this.mode.getDescription());

        LOG.debug("::: End Factor Depth Mode " + this.mode.getDescription());
    }

}
