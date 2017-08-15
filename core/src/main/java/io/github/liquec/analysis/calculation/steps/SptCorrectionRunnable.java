package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public class SptCorrectionRunnable extends Runnable {

    public SptCorrectionRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start SPT Correction Mode " + this.mode.getDescription());

        double sptCorrected = sptCalculationResult.getSptBlowCounts() * (sptCalculationResult.getEnergyRatio() / 60) * sptCalculationResult.getEffectivePressureFactor();

        if (mode == Mode.EUROCODE) {
            sptCorrected = sptCalculationResult.getDepth() < 3 ? 0.75 * sptCorrected : sptCorrected;
        }

        LOG.debug(":::::: SPT Corrected:" + sptCorrected);

        sptCalculationResult.setSptCorrected(sptCorrected);

        LOG.debug("::: End SPT Correction Mode " + this.mode.getDescription());
    }

}
