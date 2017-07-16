/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.*;
import io.github.liquec.analysis.session.SessionState;
import io.github.liquec.gui.controller.SessionController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class SessionModel {
    private static final Logger LOG = LoggerFactory.getLogger(SessionModel.class);

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToCalculate = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty normativeMode = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToRemoveLastLayer = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty ableToRemoveLastSPT = new SimpleBooleanProperty(false);

    private final SimpleStringProperty projectName;
    private final SimpleStringProperty organization;
    private final SimpleStringProperty peakGroundAceleration;
    private final SimpleStringProperty earthquakeMagnitude;
    private final SimpleStringProperty groundWaterTableDepth;

    public SessionModel(final SessionState state) {
        this.projectName = new SimpleStringProperty(state.getProjectName());
        this.organization = new SimpleStringProperty(state.getOrganization());
        this.peakGroundAceleration = new SimpleStringProperty(state.getSiteConditions().getPeakGroundAceleration() == null ? "" :
            String.valueOf(state.getSiteConditions().getPeakGroundAceleration()));
        this.earthquakeMagnitude = new SimpleStringProperty(state.getSiteConditions().getEarthquakeMagnitude() == null ? "" :
            String.valueOf(state.getSiteConditions().getEarthquakeMagnitude()));
        this.groundWaterTableDepth = new SimpleStringProperty(state.getGeotechnicalProperties().getGroundWaterTableDepth() == null ? "" :
            (String.valueOf(state.getGeotechnicalProperties().getGroundWaterTableDepth())));
        this.checkAbleToCalculate();
    }

    public String getProjectName() {
        return projectName.get();
    }

    public SimpleStringProperty projectNameProperty() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName.set(projectName);
    }

    public String getOrganization() {
        return organization.get();
    }

    public SimpleStringProperty organizationProperty() {
        return organization;
    }

    public void setOrganization(final String organization) {
        this.organization.set(organization);
    }

    public String getPeakGroundAceleration() {
        return peakGroundAceleration.get();
    }

    public SimpleStringProperty peakGroundAcelerationProperty() {
        return peakGroundAceleration;
    }

    public void setPeakGroundAceleration(final String peakGroundAceleration) {
        this.peakGroundAceleration.set(peakGroundAceleration);
    }

    public String getEarthquakeMagnitude() {
        return earthquakeMagnitude.get();
    }

    public SimpleStringProperty earthquakeMagnitudeProperty() {
        return earthquakeMagnitude;
    }

    public void setEarthquakeMagnitude(final String earthquakeMagnitude) {
        this.earthquakeMagnitude.set(earthquakeMagnitude);
    }

    public String getGroundWaterTableDepth() {
        return groundWaterTableDepth.get();
    }

    public SimpleStringProperty groundWaterTableDepthProperty() {
        return groundWaterTableDepth;
    }

    public void setGroundWaterTableDepth(final String groundWaterTableDepth) {
        this.groundWaterTableDepth.set(groundWaterTableDepth);
    }

    //

    public boolean isChangesSaved() {
        return changesSaved.get();
    }

    public SimpleBooleanProperty changesSavedProperty() {
        return changesSaved;
    }

    public void setChangesSaved(final boolean changesSaved) {
        this.changesSaved.set(changesSaved);
    }

    public boolean isAbleToCalculate() {
        return ableToCalculate.get();
    }

    public SimpleBooleanProperty ableToCalculateProperty() {
        return ableToCalculate;
    }

    public void setAbleToCalculate(final boolean ableToCalculate) {
        this.ableToCalculate.set(ableToCalculate);
    }

    public boolean isNormativeMode() {
        return normativeMode.get();
    }

    public SimpleBooleanProperty normativeModeProperty() {
        return normativeMode;
    }

    public void setNormativeMode(final boolean normativeMode) {
        this.normativeMode.set(normativeMode);
    }

    public boolean isAbleToRemoveLastLayer() {
        return ableToRemoveLastLayer.get();
    }

    public SimpleBooleanProperty ableToRemoveLastLayerProperty() {
        return ableToRemoveLastLayer;
    }

    public void setAbleToRemoveLastLayer(final boolean ableToRemoveLastLayer) {
        this.ableToRemoveLastLayer.set(ableToRemoveLastLayer);
    }

    public boolean isAbleToRemoveLastSPT() {
        return ableToRemoveLastSPT.get();
    }

    public SimpleBooleanProperty ableToRemoveLastSPTProperty() {
        return ableToRemoveLastSPT;
    }

    public void setAbleToRemoveLastSPT(final boolean ableToRemoveLastSPT) {
        this.ableToRemoveLastSPT.set(ableToRemoveLastSPT);
    }

    public SessionState getSessionState() {
        SessionState sessionState = new SessionState();
        sessionState.setProjectName(this.getProjectName());
        sessionState.setOrganization(this.getOrganization());

        SiteConditions siteConditions = new SiteConditions();
        siteConditions.setPeakGroundAceleration(StringUtils.isEmpty(this.getPeakGroundAceleration()) ? null : Float.valueOf(this.getPeakGroundAceleration()));
        siteConditions.setEarthquakeMagnitude(StringUtils.isEmpty(this.getEarthquakeMagnitude()) ? null : Float.valueOf(this.getEarthquakeMagnitude()));
        sessionState.setSiteConditions(siteConditions);

        GeotechnicalProperties geotechnicalProperties = new GeotechnicalProperties();
        geotechnicalProperties.setGroundWaterTableDepth(StringUtils.isEmpty(this.getGroundWaterTableDepth()) ? null : Float.valueOf(this.getGroundWaterTableDepth()));
        List<SoilLayer> soilLayerList = new ArrayList<>();
        geotechnicalProperties.setSoilLayers(soilLayerList);
        sessionState.setGeotechnicalProperties(geotechnicalProperties);

        List<StandardPenetrationTest> standardPenetrationTests = new ArrayList<>();
        sessionState.setStandardPenetrationTestList(standardPenetrationTests);

        return sessionState;
    }

    public void clearSessionModelData() {
        LOG.debug("Deleting session model data...");
        this.setProjectName(null);
        this.setOrganization(null);
        this.setChangesSaved(false);
        this.checkAbleToCalculate();
    }

    public void checkAbleToCalculate() {
        LOG.debug("Checking able to calculate...");
        boolean ableToCalculate = true;
        try {
            LOG.debug("peakGroundAceleration: " + this.getPeakGroundAceleration());
            if (StringUtils.isEmpty(this.getPeakGroundAceleration())) {
                throw new LiquEcException("peakGroundAceleration");
            }
            LOG.debug("earthquakeMagnitude: " + this.getEarthquakeMagnitude());
            if (StringUtils.isEmpty(this.getEarthquakeMagnitude())) {
                throw new LiquEcException("earthquakeMagnitude");
            }
            LOG.debug("groundWaterTableDepth: " + this.getGroundWaterTableDepth());
            if (StringUtils.isEmpty(this.getGroundWaterTableDepth())) {
                throw new LiquEcException("groundWaterTableDepth");
            }
        } catch (LiquEcException e) {
            LOG.debug("Empty required value: " + e.getMessage());
            ableToCalculate = false;
        }
        this.setAbleToCalculate(ableToCalculate);
        LOG.debug("isAbleToCalculate: " + this.isAbleToCalculate());
    }

    public void checkAbleToRemoveLastLayer() {
        LOG.debug("Checking able to remove last layer...");

    }

    public void checkAbleToRemoveLastSPT() {
        LOG.debug("Checking able to remove last SPT...");

    }
}
