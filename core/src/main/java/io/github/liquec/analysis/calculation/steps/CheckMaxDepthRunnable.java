/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Constant;
import io.github.liquec.analysis.calculation.Error;
import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class CheckMaxDepthRunnable extends Runnable {

    public CheckMaxDepthRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        if (sptCalculationResult.getDepth() > Constant.NO_CHECK_DEPTH.getValue(this.mode)) {
            throw new LiquEcException(Error.MAX_DEPTH_CHECK.getMessage().replace("DEPTH", String.valueOf(Constant.NO_CHECK_DEPTH.getValue(this.mode))));
        }

        this.logEnd();
    }
}
