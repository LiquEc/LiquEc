/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.model.*;
import io.github.liquec.analysis.session.SessionState;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.List;

public final class SessionModel {
    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleStringProperty projectName;
    private final SimpleStringProperty organization;

    public SessionModel(final SessionState state) {
        this.projectName = new SimpleStringProperty(state.getProjectName());
        this.organization = new SimpleStringProperty(state.getOrganization());
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

    public boolean isChangesSaved() {
        return changesSaved.get();
    }

    public SimpleBooleanProperty changesSavedProperty() {
        return changesSaved;
    }

    public void setChangesSaved(final boolean changesSaved) {
        this.changesSaved.set(changesSaved);
    }

    public SessionState getSessionState() {
        SessionState sessionState = new SessionState();
        sessionState.setProjectName(this.getProjectName());
        sessionState.setOrganization(this.getOrganization());

        SiteConditions siteConditions = new SiteConditions();
        siteConditions.setPeakGroundAceleration(null);
        siteConditions.setEarthquakeMagnitude(null);
        sessionState.setSiteConditions(siteConditions);

        GeotechnicalProperties geotechnicalProperties = new GeotechnicalProperties();
        geotechnicalProperties.setGroundWaterTableDepth(null);
        List<SoilLayer> soilLayerList = new ArrayList<>();
        geotechnicalProperties.setSoilLayers(soilLayerList);
        sessionState.setGeotechnicalProperties(geotechnicalProperties);

        List<StandardPenetrationTest> standardPenetrationTests = new ArrayList<>();
        sessionState.setStandardPenetrationTestList(standardPenetrationTests);

        return sessionState;
    }
}
