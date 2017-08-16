package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class SptCorrectionRunnable extends Runnable {

    public SptCorrectionRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        double sptCorrected = sptCalculationResult.getSptBlowCounts() * (sptCalculationResult.getEnergyRatio() / 60) * sptCalculationResult.getEffectivePressureFactor();

        if (Mode.EUROCODE.equals(this.mode)) {
            sptCorrected = sptCalculationResult.getDepth() < 3 ? 0.75 * sptCorrected : sptCorrected;
        }

        LOG.debug(":::::: SPT Corrected: " + sptCorrected + " (N60)");

        sptCalculationResult.setSptCorrected(sptCorrected);

        this.logEnd();
    }

}
