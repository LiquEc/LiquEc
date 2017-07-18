/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LayerRow {
    private static final Logger LOG = LoggerFactory.getLogger(LayerRow.class);

    private final SimpleStringProperty startDepth;
    private final SimpleStringProperty finalDepth;
    private final SimpleStringProperty soilType;
    private final SimpleStringProperty soilUnitWeightAboveGwt;
    private final SimpleStringProperty soilUnitWeightBelowGwt;
    private final SimpleStringProperty finesContent;
    private final SimpleStringProperty liquefaction;

    public LayerRow(
        final String startDepth,
        final String finalDepth,
        final String soilType,
        final String soilUnitWeightAboveGwt,
        final String soilUnitWeightBelowGwt,
        final String finesContent,
        final String liquefaction) {
        this.startDepth = new SimpleStringProperty(startDepth);
        this.finalDepth = new SimpleStringProperty(finalDepth);
        this.soilType = new SimpleStringProperty(soilType);
        this.soilUnitWeightAboveGwt = new SimpleStringProperty(soilUnitWeightAboveGwt);
        this.soilUnitWeightBelowGwt = new SimpleStringProperty(soilUnitWeightBelowGwt);
        this.finesContent = new SimpleStringProperty(finesContent);
        this.liquefaction = new SimpleStringProperty(liquefaction);
    }

    public String getStartDepth() {
        return startDepth.get();
    }

    public SimpleStringProperty startDepthProperty() {
        return startDepth;
    }

    public void setStartDepth(final String startDepth) {
        this.startDepth.set(startDepth);
    }

    public String getFinalDepth() {
        return finalDepth.get();
    }

    public SimpleStringProperty finalDepthProperty() {
        return finalDepth;
    }

    public void setFinalDepth(final String finalDepth) {
        this.finalDepth.set(finalDepth);
    }

    public String getSoilType() {
        return soilType.get();
    }

    public SimpleStringProperty soilTypeProperty() {
        return soilType;
    }

    public void setSoilType(final String soilType) {
        this.soilType.set(soilType);
    }

    public String getSoilUnitWeightAboveGwt() {
        return soilUnitWeightAboveGwt.get();
    }

    public SimpleStringProperty soilUnitWeightAboveGwtProperty() {
        return soilUnitWeightAboveGwt;
    }

    public void setSoilUnitWeightAboveGwt(final String soilUnitWeightAboveGwt) {
        this.soilUnitWeightAboveGwt.set(soilUnitWeightAboveGwt);
    }

    public String getSoilUnitWeightBelowGwt() {
        return soilUnitWeightBelowGwt.get();
    }

    public SimpleStringProperty soilUnitWeightBelowGwtProperty() {
        return soilUnitWeightBelowGwt;
    }

    public void setSoilUnitWeightBelowGwt(final String soilUnitWeightBelowGwt) {
        this.soilUnitWeightBelowGwt.set(soilUnitWeightBelowGwt);
    }

    public String getFinesContent() {
        return finesContent.get();
    }

    public SimpleStringProperty finesContentProperty() {
        return finesContent;
    }

    public void setFinesContent(final String finesContent) {
        this.finesContent.set(finesContent);
    }

    public String getLiquefaction() {
        return liquefaction.get();
    }

    public SimpleStringProperty liquefactionProperty() {
        return liquefaction;
    }

    public void setLiquefaction(final String liquefaction) {
        this.liquefaction.set(liquefaction);
    }
}
