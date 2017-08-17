package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.model.GeotechnicalProperties;
import io.github.liquec.analysis.model.SoilLayer;

import java.util.ArrayList;
import java.util.List;

public final class Helper {

    private Helper() {}

    public static int retrieveLayerIndexWithGwtInside(final GeotechnicalProperties geotechnicalProperties) {
        if (geotechnicalProperties.getGroundWaterTableDepth() == 0) {
            return 0;
        }
        int index = 0;
        int layerIndexWithGwtInside = -1;
        for (SoilLayer soilLayer : geotechnicalProperties.getSoilLayers()) {
            if (geotechnicalProperties.getGroundWaterTableDepth() > soilLayer.getStartDepth()
                && geotechnicalProperties.getGroundWaterTableDepth() <= soilLayer.getFinalDepth()) {
                layerIndexWithGwtInside = index;
            }
            index++;
        }
        return layerIndexWithGwtInside;
    }

    public static int retrieveLayerIndexWithSptInside(final List<SoilLayer> soilLayers, final Float sptDepth) {
        int index = 0;
        int layerIndexWithSptInside = -1;
        for (SoilLayer soilLayer : soilLayers) {
            if (sptDepth > soilLayer.getStartDepth()
                && sptDepth <= soilLayer.getFinalDepth()) {
                layerIndexWithSptInside = index;
            }
            index++;
        }
        return layerIndexWithSptInside;
    }

    public static List<Integer> retrieveLayerIndexesAboveGwt(final GeotechnicalProperties geotechnicalProperties, final int layerIndexWithSptInside) {
        int index = 0;
        List<Integer> layerIndexesAboveGwt = new ArrayList<>();
        for (SoilLayer soilLayer : geotechnicalProperties.getSoilLayers()) {
            if (soilLayer.getStartDepth() < geotechnicalProperties.getGroundWaterTableDepth()
                && soilLayer.getFinalDepth() <= geotechnicalProperties.getGroundWaterTableDepth()
                && index != layerIndexWithSptInside) {
                layerIndexesAboveGwt.add(index);
            }
            index++;
        }
        return layerIndexesAboveGwt;
    }

    public static List<Integer> retrieveLayerIndexesBelowGwt(final GeotechnicalProperties geotechnicalProperties, final int layerIndexWithSptInside, final Float sptDepth) {
        int index = 0;
        List<Integer> layerIndexesBelowGwt = new ArrayList<>();
        for (SoilLayer soilLayer : geotechnicalProperties.getSoilLayers()) {
            if (soilLayer.getStartDepth() >= geotechnicalProperties.getGroundWaterTableDepth()
                && soilLayer.getFinalDepth() > geotechnicalProperties.getGroundWaterTableDepth()
                && index != layerIndexWithSptInside
                && soilLayer.getStartDepth() < sptDepth) {
                layerIndexesBelowGwt.add(index);
            }
            index++;
        }
        return layerIndexesBelowGwt;
    }

}
