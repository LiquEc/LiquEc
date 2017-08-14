package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class SptCorrectedRunnable extends Runnable {

    public SptCorrectedRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start SPT Corrected Mode " + this.mode.getDescription());

        double sptCorrected = sptCalculationResult.getSptBlowCounts() * (sptCalculationResult.getEnergyRatio() / 60) * sptCalculationResult.getFactorEffectivePressure();

        if (mode == Mode.EUROCODE) {
            sptCorrected = sptCalculationResult.getDepth() < 3 ? 0.75 * sptCorrected : sptCorrected;
        }

        LOG.debug(":::::: FSPT corrected:" + sptCorrected);

        sptCalculationResult.setSptCorrected(sptCorrected);

        LOG.debug("::: End SPT Corrected Mode " + this.mode.getDescription());
    }

}
