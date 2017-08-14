package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class CoefficientOfContributionRunnable extends Runnable {

    public CoefficientOfContributionRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Coefficient Of Contribution Mode " + this.mode.getDescription());

        final double coefficientOfContribution = 1.5 - (1.8 * Math.pow(sessionState.getSiteConditions().getCoefficientOfContribution() - 1, 1/2));

        LOG.debug(":::::: Coefficient of contribution:" + coefficientOfContribution);

        sptCalculationResult.setCoefficientOfContribution(coefficientOfContribution);

        LOG.debug("::: End Coefficient Of Contribution Mode " + this.mode.getDescription());
    }

}
