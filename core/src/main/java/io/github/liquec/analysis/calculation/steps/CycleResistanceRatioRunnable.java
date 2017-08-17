package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.*;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.calculation.ranges.crr.Crr;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

import java.lang.reflect.InvocationTargetException;

public class CycleResistanceRatioRunnable extends Runnable {

    public CycleResistanceRatioRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.logStart();

        final int layerIndexWithSptInside = Helper.retrieveLayerIndexWithSptInside(sessionState.getGeotechnicalProperties().getSoilLayers(), sptCalculationResult.getDepth());
        LOG.debug(":::::: Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquEcException(io.github.liquec.analysis.calculation.Error.LAYER_WITH_SPT_INSIDE_NOT_FOUND.getMessage());
        }

        final Float finesContent = sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithSptInside).getFinesContent();
        LOG.debug(":::::: Layer fines content: " + finesContent + " (%)");

        for (Crr crr : Crr.values()) {

            final boolean contains = crr.getCrrEvaluationClass().getConstructor().newInstance().contains(Double.valueOf(finesContent));
            LOG.debug(":::::: Fines contains: " + contains);

            if (contains) {
                final Double cycleResistanceRatio =  crr.getBounds().length == 1
                    ? this.retrieveValue(crr, 0, sptCalculationResult.getSptCorrected())
                    : this.retrieveOffsetValue(crr, sptCalculationResult.getSptCorrected(), (Double.valueOf(finesContent)));
                sptCalculationResult.setCycleResistanceRatio(cycleResistanceRatio);
                break;
            }

        }

        this.logEnd();
    }

    private Double retrieveValue(final Crr crr, final int index, final Double sptCorrected)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Double cycleResistanceRatio = 0.0;
        LOG.debug(":::::: SPT Corrected: " + sptCorrected + " (N60)");
        for (Enum<? extends Evaluation> fines : crr.getEvaluations(this.mode)[index]) {

            final boolean contains = ((Evaluation) fines).getEvaluationClass().getConstructor().newInstance().contains(sptCorrected);
            LOG.debug(":::::: SPT corrected contains: " + contains);

            if (contains) {
                cycleResistanceRatio = ((Evaluation) fines).getPolynomial().getValue(sptCorrected);
                LOG.debug(":::::: CRR: " + cycleResistanceRatio);
                break;
            }

        }
        return cycleResistanceRatio;
    }

    private Double retrieveOffsetValue(final Crr crr, final Double sptCorrected, final Double finesContent)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Double crr1 = this.retrieveValue(crr, 0, sptCorrected);
        LOG.debug(":::::: CRR first: " + crr1);

        final Double crr2 = this.retrieveValue(crr, 1, sptCorrected);
        LOG.debug(":::::: CRR second: " + crr2);

        final Double cycleResistanceRatio = ((crr1 * (finesContent - crr.getBounds()[0].getBound()))
            + (crr2 * (crr.getBounds()[1].getBound() - finesContent))) / (crr.getBounds()[1].getBound() - crr.getBounds()[0].getBound());
        LOG.debug(":::::: Final CRR: " + cycleResistanceRatio);

        return cycleResistanceRatio;
    }

}
