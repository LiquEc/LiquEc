package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class CoefficientContributionCorrectionRunnable extends Runnable {

    public CoefficientContributionCorrectionRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final double coefficientContributionCorrection = 1.5 - (1.8 * Math.pow(sessionState.getSiteConditions().getCoefficientOfContribution() - 1.0, 0.5));

        LOG.debug(":::::: Coefficient contribution correction (KM): " + coefficientContributionCorrection);

        sptCalculationResult.setCoefficientContributionCorrection(coefficientContributionCorrection);

        this.logEnd();
    }

}
