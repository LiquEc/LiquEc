/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Error;
import io.github.liquec.analysis.calculation.Helper;
import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.GeotechnicalProperties;
import io.github.liquec.analysis.model.SoilLayer;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TotalStressRunnable extends Runnable {

    public TotalStressRunnable(final Mode mode, final String description) {
        super(mode, description);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        this.logStart();

        final int layerIndexWithGwtInside = Helper.retrieveLayerIndexWithGwtInside(sessionState.getGeotechnicalProperties());
        LOG.debug(":::::: Layer index with GWT inside: " + layerIndexWithGwtInside);

        if (layerIndexWithGwtInside == -1) {
            throw new LiquEcException(Error.LAYER_WITH_GWT_INSIDE_NOT_FOUND.getMessage());
        }

        final int layerIndexWithSptInside = Helper.retrieveLayerIndexWithSptInside(sessionState.getGeotechnicalProperties().getSoilLayers(), sptCalculationResult.getDepth());
        LOG.debug(":::::: Layer index with SPT inside: " + layerIndexWithSptInside);

        if (layerIndexWithSptInside == -1) {
            throw new LiquEcException(Error.LAYER_WITH_SPT_INSIDE_NOT_FOUND.getMessage());
        }

        final List<Integer> layerIndexesAboveGwt = Helper.retrieveLayerIndexesAboveGwt(sessionState.getGeotechnicalProperties(), layerIndexWithSptInside);
        LOG.debug(":::::: Layer indexes above GWT: " + Arrays.toString(layerIndexesAboveGwt.toArray()));

        final List<Integer> layerIndexesBelowGwt = Helper.retrieveLayerIndexesBelowGwt(sessionState.getGeotechnicalProperties(), layerIndexWithSptInside, sptCalculationResult.getDepth());
        LOG.debug(":::::: Layer indexes below GWT: " + Arrays.toString(layerIndexesBelowGwt.toArray()));

        double totalStress = this.retrieveStressFromLayersAboveGwt(sessionState.getGeotechnicalProperties().getSoilLayers(), layerIndexesAboveGwt);

        totalStress += this.retrieveAboveStressFromLayerWithGwtInside(sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithGwtInside),
            sessionState.getGeotechnicalProperties().getGroundWaterTableDepth());

        if (layerIndexWithGwtInside == layerIndexWithSptInside) {

            totalStress += this.retrieveBelowStressFromLayerWithGwtAndSptInside(sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithGwtInside),
                sessionState.getGeotechnicalProperties().getGroundWaterTableDepth(),  sptCalculationResult.getDepth());

        } else {

            totalStress += this.retrieveBelowStressFromLayerWithGwtInside(sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithGwtInside),
                sessionState.getGeotechnicalProperties().getGroundWaterTableDepth());

            totalStress += this.retrieveStressFromLayersBelowGwt(sessionState.getGeotechnicalProperties().getSoilLayers(), layerIndexesBelowGwt);

            totalStress += this.retrieveBelowStressFromLayerWithSptInside(sessionState.getGeotechnicalProperties().getSoilLayers().get(layerIndexWithSptInside), sptCalculationResult.getDepth());
        }

        LOG.debug(":::::: Total stress:" + totalStress + " KN/m2");

        sptCalculationResult.setTotalStress(totalStress);

        this.logEnd();
    }

    private double retrieveStressFromLayersAboveGwt(final List<SoilLayer> soilLayers, final List<Integer> layerIndexesAboveGwt) {
        double stress = 0;
        for (Integer index : layerIndexesAboveGwt) {
            stress += soilLayers.get(index).getSoilUnitWeight().getAboveGwt() * (soilLayers.get(index).getFinalDepth() - soilLayers.get(index).getStartDepth());
        }
        LOG.debug(":::::: Stress from layers above GWT: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveAboveStressFromLayerWithGwtInside(final SoilLayer soilLayer, final Float groundWaterTableDepth) {
        final double stress = soilLayer.getSoilUnitWeight().getAboveGwt() * (groundWaterTableDepth - soilLayer.getStartDepth());
        LOG.debug(":::::: Above stress from layer with GWT inside: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveBelowStressFromLayerWithGwtAndSptInside(final SoilLayer soilLayer, final Float groundWaterTableDepth, final Float sptDepth) {
        final double stress = soilLayer.getSoilUnitWeight().getBelowGwt() * (sptDepth - groundWaterTableDepth);
        LOG.debug(":::::: Below stress from layer with GWT and SPT inside: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveBelowStressFromLayerWithGwtInside(final SoilLayer soilLayer, final Float groundWaterTableDepth) {
        final double stress = soilLayer.getSoilUnitWeight().getBelowGwt() * (soilLayer.getFinalDepth() - groundWaterTableDepth);
        LOG.debug(":::::: Below stress from layer with GWT inside: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveStressFromLayersBelowGwt(final List<SoilLayer> soilLayers, final List<Integer> layerIndexesBelowGwt) {
        double stress = 0;
        for (Integer index : layerIndexesBelowGwt) {
            stress += soilLayers.get(index).getSoilUnitWeight().getBelowGwt() * (soilLayers.get(index).getFinalDepth() - soilLayers.get(index).getStartDepth());
        }
        LOG.debug(":::::: Stress from layers below GWT: " + stress + " KN/m2");
        return stress;
    }

    private double retrieveBelowStressFromLayerWithSptInside(final SoilLayer soilLayer, final Float sptDepth) {
        final double stress = soilLayer.getSoilUnitWeight().getBelowGwt() * (sptDepth - soilLayer.getStartDepth());
        LOG.debug(":::::: Below stress from layer with SPT inside: " + stress + " KN/m2");
        return stress;
    }
}
