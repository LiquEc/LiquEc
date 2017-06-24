/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.model;

public class SoilLayer {
    private Float startDepth;
    private Float finalDepth;
    private SoilType soilType;
    private SoilUnitWeight soilUnitWeight;
    private Float finesContent;
    private Boolean checkLiquefaction;

    public SoilLayer() {
        // No argument constructor to allow use as standard Java Bean
    }

    public SoilLayer(final Float startDepth, final Float finalDepth, final SoilType soilType, final SoilUnitWeight soilUnitWeight, final Float finesContent, final Boolean checkLiquefaction) {
        this.startDepth = startDepth;
        this.finalDepth = finalDepth;
        this.soilType = soilType;
        this.soilUnitWeight = soilUnitWeight;
        this.finesContent = finesContent;
        this.checkLiquefaction = checkLiquefaction;
    }

    public Float getStartDepth() {
        return startDepth;
    }

    public void setStartDepth(final Float startDepth) {
        this.startDepth = startDepth;
    }

    public Float getFinalDepth() {
        return finalDepth;
    }

    public void setFinalDepth(final Float finalDepth) {
        this.finalDepth = finalDepth;
    }

    public SoilType getSoilType() {
        return soilType;
    }

    public void setSoilType(final SoilType soilType) {
        this.soilType = soilType;
    }

    public SoilUnitWeight getSoilUnitWeight() {
        return soilUnitWeight;
    }

    public void setSoilUnitWeight(final SoilUnitWeight soilUnitWeight) {
        this.soilUnitWeight = soilUnitWeight;
    }

    public Float getFinesContent() {
        return finesContent;
    }

    public void setFinesContent(final Float finesContent) {
        this.finesContent = finesContent;
    }

    public Boolean getCheckLiquefaction() {
        return checkLiquefaction;
    }

    public void setCheckLiquefaction(final Boolean checkLiquefaction) {
        this.checkLiquefaction = checkLiquefaction;
    }
}
