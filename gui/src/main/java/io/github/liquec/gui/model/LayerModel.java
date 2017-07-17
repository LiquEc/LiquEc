/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.GeotechnicalProperties;
import io.github.liquec.analysis.model.SiteConditions;
import io.github.liquec.analysis.model.SoilLayer;
import io.github.liquec.analysis.model.StandardPenetrationTest;
import io.github.liquec.analysis.session.SessionState;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class LayerModel {
    private static final Logger LOG = LoggerFactory.getLogger(LayerModel.class);

    private final SimpleBooleanProperty ableToAdd = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty layerLiquefaction = new SimpleBooleanProperty(true);

    private final SimpleStringProperty startDepth;
    private final SimpleStringProperty finalDepth;
    private final SimpleStringProperty soilType;
    private final SimpleStringProperty soilUnitWeightAboveGwt;
    private final SimpleStringProperty soilUnitWeightBelowGwt;
    private final SimpleStringProperty finesContent;

    public LayerModel(final String startDepth) {
        this.startDepth = new SimpleStringProperty(startDepth);
        this.finalDepth = new SimpleStringProperty();
        this.soilType = new SimpleStringProperty();
        this.soilUnitWeightAboveGwt = new SimpleStringProperty();
        this.soilUnitWeightBelowGwt = new SimpleStringProperty();
        this.finesContent = new SimpleStringProperty();
        this.checkAbleToAdd();
    }

    public boolean isAbleToAdd() {
        return ableToAdd.get();
    }

    public SimpleBooleanProperty ableToAddProperty() {
        return ableToAdd;
    }

    public void setAbleToAdd(final boolean ableToAdd) {
        this.ableToAdd.set(ableToAdd);
    }

    public boolean isLayerLiquefaction() {
        return layerLiquefaction.get();
    }

    public SimpleBooleanProperty layerLiquefactionProperty() {
        return layerLiquefaction;
    }

    public void setLayerLiquefaction(final boolean layerLiquefaction) {
        this.layerLiquefaction.set(layerLiquefaction);
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

    public void checkAbleToAdd() {
        LOG.debug("Checking able to add...");
        boolean ableToAdd = true;
        try {
            LOG.debug("startDepth: " + this.getStartDepth());
            if (StringUtils.isEmpty(this.getStartDepth())) {
                throw new LiquEcException("startDepth");
            }
            LOG.debug("finalDepth: " + this.getFinalDepth());
            if (StringUtils.isEmpty(this.getFinalDepth())) {
                throw new LiquEcException("finalDepth");
            }
            LOG.debug("soilType: " + this.getSoilType());
            if (StringUtils.isEmpty(this.getSoilType())) {
                throw new LiquEcException("soilType");
            }
            LOG.debug("soilUnitWeightAboveGwt: " + this.getSoilUnitWeightAboveGwt());
            if (StringUtils.isEmpty(this.getSoilUnitWeightAboveGwt())) {
                throw new LiquEcException("soilUnitWeightAboveGwt");
            }
            LOG.debug("soilUnitWeightBelowGwt: " + this.getSoilUnitWeightBelowGwt());
            if (StringUtils.isEmpty(this.getSoilUnitWeightBelowGwt())) {
                throw new LiquEcException("soilUnitWeightBelowGwt");
            }
            LOG.debug("finesContent: " + this.getFinesContent());
            if (StringUtils.isEmpty(this.getFinesContent())) {
                throw new LiquEcException("finesContent");
            }
        } catch (LiquEcException e) {
            LOG.debug("Empty required value: " + e.getMessage());
            ableToAdd = false;
        }
        this.setAbleToAdd(ableToAdd);
        LOG.debug("isAbleToAdd: " + this.isAbleToAdd());
    }
}
