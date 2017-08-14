/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.steps;

import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.calculation.Runnable;
import io.github.liquec.analysis.model.GeotechnicalProperties;
import io.github.liquec.analysis.model.SoilLayer;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TotalStressRunnable extends Runnable {

    public TotalStressRunnable(final Mode mode) {
        super(mode);
    }

    public void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) {
        LOG.debug("::: Start Total Stress Mode " + this.mode.getDescription());

        // Retrieve layer index with GWT inside
        final int layerIndexWithGwtInside = this.retrieveLayerIndexWithGwtInside(sessionState.getGeotechnicalProperties());
        LOG.debug(":::::: Layer index with GWT inside: " + layerIndexWithGwtInside);

        // Retrieve layer index with SPT inside
        final int layerIndexWithSptInside = this.retrieveLayerIndexWithSptInside(sessionState.getGeotechnicalProperties().getSoilLayers(), sptCalculationResult.getDepth());
        LOG.debug(":::::: Layer index with SPT inside: " + layerIndexWithSptInside);

        // Retrieve layer indexes above GWT
        final List<Integer> layerIndexesAboveGwt = this.retrieveLayerIndexesAboveGwt(sessionState.getGeotechnicalProperties(), layerIndexWithSptInside);
        LOG.debug(":::::: Layer indexes above GWT: " + Arrays.toString(layerIndexesAboveGwt.toArray()));

        // Retrieve layer indexes below GWT
        final List<Integer> layerIndexesBelowGwt = this.retrieveLayerIndexesBelowGwt(sessionState.getGeotechnicalProperties(), layerIndexWithSptInside, sptCalculationResult.getDepth());
        LOG.debug(":::::: Layer indexes below GWT: " + Arrays.toString(layerIndexesBelowGwt.toArray()));

        // Calculate total stress
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

        LOG.debug("::: End Total Stress Mode " + this.mode.getDescription());
    }

    private int retrieveLayerIndexWithGwtInside(final GeotechnicalProperties geotechnicalProperties) {
        int index = 0;
        int layerIndexWithGwtInside = -1;
        for (SoilLayer soilLayer : geotechnicalProperties.getSoilLayers()) {
            if(geotechnicalProperties.getGroundWaterTableDepth() > soilLayer.getStartDepth() &&
               geotechnicalProperties.getGroundWaterTableDepth() <= soilLayer.getFinalDepth()) {
                layerIndexWithGwtInside = index;
            }
            index++;
        }
        return layerIndexWithGwtInside;
    }

    private int retrieveLayerIndexWithSptInside(final List<SoilLayer> soilLayers, final Float sptDepth) {
        int index = 0;
        int layerIndexWithSptInside = -1;
        for (SoilLayer soilLayer : soilLayers) {
            if(sptDepth > soilLayer.getStartDepth() &&
               sptDepth <= soilLayer.getFinalDepth()) {
                layerIndexWithSptInside = index;
            }
            index++;
        }
        return layerIndexWithSptInside;
    }

    private List<Integer> retrieveLayerIndexesAboveGwt(final GeotechnicalProperties geotechnicalProperties, final int layerIndexWithSptInside) {
        int index = 0;
        List<Integer> layerIndexesAboveGwt = new ArrayList<>();
        for (SoilLayer soilLayer : geotechnicalProperties.getSoilLayers()) {
            if(soilLayer.getStartDepth() < geotechnicalProperties.getGroundWaterTableDepth() &&
               soilLayer.getFinalDepth() <= geotechnicalProperties.getGroundWaterTableDepth() &&
               index != layerIndexWithSptInside) {
                layerIndexesAboveGwt.add(index);
            }
            index++;
        }
        return layerIndexesAboveGwt;
    }

    private List<Integer> retrieveLayerIndexesBelowGwt(final GeotechnicalProperties geotechnicalProperties, final int layerIndexWithSptInside, final Float sptDepth) {
        int index = 0;
        List<Integer> layerIndexesBelowGwt = new ArrayList<>();
        for (SoilLayer soilLayer : geotechnicalProperties.getSoilLayers()) {
            if(soilLayer.getStartDepth() >= geotechnicalProperties.getGroundWaterTableDepth() &&
               soilLayer.getFinalDepth() > geotechnicalProperties.getGroundWaterTableDepth() &&
               index != layerIndexWithSptInside &&
               soilLayer.getStartDepth() < sptDepth) {
                layerIndexesBelowGwt.add(index);
            }
            index++;
        }
        return layerIndexesBelowGwt;
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
