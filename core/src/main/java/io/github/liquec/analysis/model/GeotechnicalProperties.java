/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

import java.util.ArrayList;
import java.util.List;

public final class GeotechnicalProperties {
    private Float groundWaterTableDepth;
    private List<SoilLayer> soilLayers = new ArrayList<>();

    public GeotechnicalProperties() {
        // No argument constructor to allow use as standard Java Bean
    }

    public GeotechnicalProperties(final Float groundWaterTableDepth, final List<SoilLayer> soilLayers) {
        this.groundWaterTableDepth = groundWaterTableDepth;
        this.soilLayers = soilLayers;
    }

    public Float getGroundWaterTableDepth() {
        return groundWaterTableDepth;
    }

    public void setGroundWaterTableDepth(final Float groundWaterTableDepth) {
        this.groundWaterTableDepth = groundWaterTableDepth;
    }

    public List<SoilLayer> getSoilLayers() {
        return soilLayers;
    }

    public void setSoilLayers(final List<SoilLayer> soilLayers) {
        this.soilLayers = soilLayers;
    }
}
