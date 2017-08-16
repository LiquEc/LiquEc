/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.*;
import io.github.liquec.analysis.session.SessionState;
import io.github.liquec.gui.chart.LiquEcData;
import io.github.liquec.gui.common.BoundsEnum;
import io.github.liquec.gui.common.DefaultValuesEnum;
import io.github.liquec.gui.common.LiquefactionEnum;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SessionModel {
    private static final Logger LOG = LoggerFactory.getLogger(SessionModel.class);

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToCalculate = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty normativeMode = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToAddLayer = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToRemoveLastLayer = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty ableToAddSpt = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty ableToRemoveLastSpt = new SimpleBooleanProperty(false);

    private final SimpleStringProperty projectName;
    private final SimpleStringProperty projectLocation;
    private final SimpleStringProperty organization;
    private final SimpleStringProperty peakGroundAcceleration;
    private final SimpleStringProperty earthquakeMagnitude;
    private final SimpleStringProperty coefficientOfContribution;
    private final SimpleStringProperty groundWaterTableDepth;

    private final ObservableList<LayerRow> layerData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> layerChartData = FXCollections.observableArrayList();

    private final ObservableList<SptRow> sptData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> sptChartData = FXCollections.observableArrayList();

    public SessionModel(final SessionState state) {
        this.projectName = new SimpleStringProperty(state.getProjectName());
        this.projectLocation = new SimpleStringProperty(state.getProjectLocation());
        this.organization = new SimpleStringProperty(state.getOrganization());
        this.peakGroundAcceleration = new SimpleStringProperty(state.getSiteConditions().getPeakGroundAcceleration() == null ? "" :
            String.valueOf(state.getSiteConditions().getPeakGroundAcceleration()));
        this.earthquakeMagnitude = new SimpleStringProperty(state.getSiteConditions().getEarthquakeMagnitude() == null ? DefaultValuesEnum.EARTHQUAKE_MAGNITUDE.getValue() :
            String.valueOf(state.getSiteConditions().getEarthquakeMagnitude()));
        this.coefficientOfContribution = new SimpleStringProperty(state.getSiteConditions().getCoefficientOfContribution() == null ? DefaultValuesEnum.COEFFICIENT_OF_CONTRIBUTION.getValue() :
            String.valueOf(state.getSiteConditions().getCoefficientOfContribution()));
        this.groundWaterTableDepth = new SimpleStringProperty(state.getGeotechnicalProperties().getGroundWaterTableDepth() == null ? "" :
            (this.getFormattedDepth(String.valueOf(state.getGeotechnicalProperties().getGroundWaterTableDepth()))));
        this.initializeLayerData(state.getGeotechnicalProperties().getSoilLayers());
        this.initializeLayerChartData(state.getGeotechnicalProperties().getSoilLayers());
        Collections.sort(state.getStandardPenetrationTestList());
        this.initializeSptData(state.getStandardPenetrationTestList());
        this.initializeSptChartData(state.getStandardPenetrationTestList());
        this.checkAbleToCalculate();
        this.checkAbleToRemoveLastLayer();
        this.checkAbleToAddSpt();
        this.checkAbleToRemoveLastSpt();
    }

    private void initializeLayerData(final List<SoilLayer> soilLayers) {
        for (SoilLayer soilLayer : soilLayers) {
            layerData.add(this.buildLayerRow(soilLayer));
        }
    }

    private void initializeLayerChartData(final List<SoilLayer> soilLayers) {
        for (SoilLayer soilLayer : soilLayers) {
            final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
            series.setName(soilLayer.getSoilType());
            final ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
            data.add(LiquEcData.getChartInverseDataLowerX(soilLayer.getFinalDepth() - soilLayer.getStartDepth()));
            data.add(LiquEcData.getChartInverseDataUpperX(soilLayer.getFinalDepth() - soilLayer.getStartDepth()));
            series.setData(data);
            layerChartData.add(series);
        }
    }

    private LayerRow buildLayerRow(final SoilLayer soilLayer) {
        return new LayerRow(
            this.getFormattedDepth(String.valueOf(soilLayer.getStartDepth())),
            this.getFormattedDepth(String.valueOf(soilLayer.getFinalDepth())),
            String.valueOf(soilLayer.getSoilType()),
            String.valueOf(soilLayer.getSoilUnitWeight().getAboveGwt()),
            String.valueOf(soilLayer.getSoilUnitWeight().getBelowGwt()),
            String.valueOf(soilLayer.getFinesContent()),
            LiquefactionEnum.getDescription(soilLayer.getCheckLiquefaction()));
    }

    private void initializeSptData(final List<StandardPenetrationTest> standardPenetrationTestList) {
        for (StandardPenetrationTest standardPenetrationTest : standardPenetrationTestList) {
            sptData.add(this.buildSptRow(standardPenetrationTest));
        }
    }

    private void initializeSptChartData(final List<StandardPenetrationTest> standardPenetrationTestList) {
        sptChartData.clear();
        // spt points
        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("SPT series");
        for (StandardPenetrationTest standardPenetrationTest : standardPenetrationTestList) {
            series.getData().add(LiquEcData.getChartInverseDataSpt(standardPenetrationTest.getSptBlowCounts(), standardPenetrationTest.getDepth()));
        }
        sptChartData.add(series);
    }

    private SptRow buildSptRow(final StandardPenetrationTest standardPenetrationTest) {
        return new SptRow(
            this.getFormattedDepth(String.valueOf(standardPenetrationTest.getDepth())),
            String.valueOf(standardPenetrationTest.getSptBlowCounts()),
            String.valueOf(standardPenetrationTest.getEnergyRatio())
        );
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

    public String getProjectLocation() {
        return projectLocation.get();
    }

    public SimpleStringProperty projectLocationProperty() {
        return projectLocation;
    }

    public void setProjectLocation(final String projectLocation) {
        this.projectLocation.set(projectLocation);
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

    public String getPeakGroundAcceleration() {
        return peakGroundAcceleration.get();
    }

    public SimpleStringProperty peakGroundAccelerationProperty() {
        return peakGroundAcceleration;
    }

    public void setPeakGroundAcceleration(final String peakGroundAcceleration) {
        this.peakGroundAcceleration.set(peakGroundAcceleration);
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

    public String getCoefficientOfContribution() {
        return coefficientOfContribution.get();
    }

    public SimpleStringProperty coefficientOfContributionProperty() {
        return coefficientOfContribution;
    }

    public void setCoefficientOfContribution(final String coefficientOfContribution) {
        this.coefficientOfContribution.set(coefficientOfContribution);
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

    public ObservableList<LayerRow> getLayerData() {
        return layerData;
    }

    public void removeLastLayer() {
        if (this.layerData.size() > 0) {
            this.layerData.remove(this.layerData.size() - 1);
        }
    }

    public ObservableList<XYChart.Series<Number, Number>> getLayerChartData() {
        return layerChartData;
    }

    public void removeLastChartLayer() {
        if (this.layerChartData.size() > 0) {
            this.layerChartData.remove(this.layerChartData.size() - 1);
        }
    }

    public ObservableList<SptRow> getSptData() {
        return sptData;
    }

    public void removeLastSpt() {
        if (this.sptData.size() > 0) {
            this.sptData.remove(this.sptData.size() - 1);
        }
    }

    public ObservableList<XYChart.Series<Number, Number>> getSptChartData() {
        return sptChartData;
    }

    public XYChart.Series<Number, Number> getSptChartMainDataSeries() {
        return sptChartData.get(0);
    }

    public void removeLastChartSpt() {
        sptChartData.clear();
        // spt points
        final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("SPT series");
        for (SptRow sptRow : this.sptData) {
            series.getData().add(LiquEcData.getChartInverseDataSpt(Integer.valueOf(sptRow.getSptBlowCounts()), Float.valueOf(sptRow.getSptDepth())));
        }
        sptChartData.add(series);
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

    public boolean isAbleToAddLayer() {
        return ableToAddLayer.get();
    }

    public SimpleBooleanProperty ableToAddLayerProperty() {
        return ableToAddLayer;
    }

    public void setAbleToAddLayer(final boolean ableToAddLayer) {
        this.ableToAddLayer.set(ableToAddLayer);
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

    public boolean isAbleToAddSpt() {
        return ableToAddSpt.get();
    }

    public SimpleBooleanProperty ableToAddSptProperty() {
        return ableToAddSpt;
    }

    public void setAbleToAddSpt(final boolean ableToAddSpt) {
        this.ableToAddSpt.set(ableToAddSpt);
    }

    public boolean isAbleToRemoveLastSpt() {
        return ableToRemoveLastSpt.get();
    }

    public SimpleBooleanProperty ableToRemoveLastSptProperty() {
        return ableToRemoveLastSpt;
    }

    public void setAbleToRemoveLastSpt(final boolean ableToRemoveLastSpt) {
        this.ableToRemoveLastSpt.set(ableToRemoveLastSpt);
    }

    public SessionState getSessionState() {
        SessionState sessionState = new SessionState();
        sessionState.setProjectName(this.getProjectName());
        sessionState.setProjectLocation(this.getProjectLocation());
        sessionState.setOrganization(this.getOrganization());

        SiteConditions siteConditions = new SiteConditions();
        siteConditions.setPeakGroundAcceleration(StringUtils.isEmpty(this.getPeakGroundAcceleration()) ? null : Float.valueOf(this.getPeakGroundAcceleration()));
        siteConditions.setEarthquakeMagnitude(StringUtils.isEmpty(this.getEarthquakeMagnitude()) ? null : Float.valueOf(this.getEarthquakeMagnitude()));
        siteConditions.setCoefficientOfContribution(StringUtils.isEmpty(this.getCoefficientOfContribution()) ? null : Float.valueOf(this.getCoefficientOfContribution()));
        sessionState.setSiteConditions(siteConditions);

        GeotechnicalProperties geotechnicalProperties = new GeotechnicalProperties();
        geotechnicalProperties.setGroundWaterTableDepth(StringUtils.isEmpty(this.getGroundWaterTableDepth()) ? null : Float.valueOf(this.getGroundWaterTableDepth()));
        geotechnicalProperties.setSoilLayers(this.buildSoilLayerList());
        sessionState.setGeotechnicalProperties(geotechnicalProperties);

        sessionState.setStandardPenetrationTestList(this.buildStandardPenetrationTests());

        return sessionState;
    }

    private List<SoilLayer> buildSoilLayerList() {
        final List<SoilLayer> soilLayerList = new ArrayList<>();
        for (LayerRow layerRow : this.getLayerData()) {
            soilLayerList.add(this.buildSoilLayer(layerRow));
        }
        return soilLayerList;
    }

    private SoilLayer buildSoilLayer(final LayerRow layerRow) {
        final SoilLayer soilLayer = new SoilLayer();
        soilLayer.setStartDepth(StringUtils.isEmpty(layerRow.getStartDepth()) ? null : Float.valueOf(layerRow.getStartDepth()));
        soilLayer.setFinalDepth(StringUtils.isEmpty(layerRow.getFinalDepth()) ? null : Float.valueOf(layerRow.getFinalDepth()));
        soilLayer.setSoilType(layerRow.getSoilType());
        final SoilUnitWeight soilUnitWeight = new SoilUnitWeight();
        soilUnitWeight.setAboveGwt(StringUtils.isEmpty(layerRow.getSoilUnitWeightAboveGwt()) ? null : Float.valueOf(layerRow.getSoilUnitWeightAboveGwt()));
        soilUnitWeight.setBelowGwt(StringUtils.isEmpty(layerRow.getSoilUnitWeightBelowGwt()) ? null : Float.valueOf(layerRow.getSoilUnitWeightBelowGwt()));
        soilLayer.setSoilUnitWeight(soilUnitWeight);
        soilLayer.setFinesContent(StringUtils.isEmpty(layerRow.getFinesContent()) ? null : Float.valueOf(layerRow.getFinesContent()));
        soilLayer.setCheckLiquefaction(LiquefactionEnum.getLiquefaction(layerRow.getLiquefaction()));
        return soilLayer;
    }

    private List<StandardPenetrationTest> buildStandardPenetrationTests() {
        List<StandardPenetrationTest> standardPenetrationTests = new ArrayList<>();
        for (SptRow sptRow : this.getSptData()) {
            standardPenetrationTests.add(this.buildStandardPenetrationTest(sptRow));
        }
        return standardPenetrationTests;
    }

    private StandardPenetrationTest buildStandardPenetrationTest(final SptRow sptRow) {
        StandardPenetrationTest standardPenetrationTest = new StandardPenetrationTest();
        standardPenetrationTest.setDepth(StringUtils.isEmpty(sptRow.getSptDepth()) ? null : Float.valueOf(sptRow.getSptDepth()));
        standardPenetrationTest.setSptBlowCounts(StringUtils.isEmpty(sptRow.getSptBlowCounts()) ? null : Integer.valueOf(sptRow.getSptBlowCounts()));
        standardPenetrationTest.setEnergyRatio(StringUtils.isEmpty(sptRow.getSptEnergyRatio()) ? null : Float.valueOf(sptRow.getSptEnergyRatio()));
        return standardPenetrationTest;
    }

    public void clearSessionModelData() {
        LOG.debug("Deleting session model data...");
        this.setProjectName(null);
        this.setProjectLocation(null);
        this.setOrganization(null);
        this.setPeakGroundAcceleration(null);
        this.setEarthquakeMagnitude(null);
        this.setCoefficientOfContribution(null);
        this.setGroundWaterTableDepth(null);
        this.layerData.clear();
        this.layerChartData.clear();
        this.sptData.clear();
        this.getSptChartMainDataSeries().getData().clear();
        this.setChangesSaved(false);
        this.checkAbleToCalculate();
        this.checkAbleToRemoveLastLayer();
        this.checkAbleToAddSpt();
        this.checkAbleToRemoveLastSpt();
    }

    public void checkAbleToCalculate() {
        LOG.debug("Checking able to calculate...");
        boolean ableToCalculate = true;
        try {
            LOG.debug("peakGroundAcceleration: " + this.getPeakGroundAcceleration());
            if (StringUtils.isEmpty(this.getPeakGroundAcceleration()) || Float.valueOf(this.getPeakGroundAcceleration()) == 0) {
                throw new LiquEcException("peakGroundAcceleration");
            }
            if (this.normativeMode.get()) {
                LOG.debug("normativeMode: Eurocode");
                LOG.debug("earthquakeMagnitude: " + this.getEarthquakeMagnitude());
                if (StringUtils.isEmpty(this.getEarthquakeMagnitude()) || Float.valueOf(this.getEarthquakeMagnitude()) == 0) {
                    throw new LiquEcException("earthquakeMagnitude");
                }
            } else {
                LOG.debug("normativeMode: NCSE-02");
                LOG.debug("coefficientOfContribution: " + this.getCoefficientOfContribution());
                if (StringUtils.isEmpty(this.getCoefficientOfContribution()) || Float.valueOf(this.getCoefficientOfContribution()) == 0) {
                    throw new LiquEcException("coefficientOfContribution");
                }
            }
            LOG.debug("groundWaterTableDepth: " + this.getGroundWaterTableDepth());
            if (StringUtils.isEmpty(this.getGroundWaterTableDepth())) { // can be zero
                throw new LiquEcException("groundWaterTableDepth");
            }
            LOG.debug("layers: " + this.layerData.size());
            if (this.layerData.size() == 0) {
                throw new LiquEcException("geotechnicalLayer");
            }
            LOG.debug("spt: " + this.layerData.size());
            if (this.sptData.size() == 0) {
                throw new LiquEcException("spt");
            }
        } catch (LiquEcException e) {
            LOG.debug("Required value: " + e.getMessage());
            ableToCalculate = false;
        }
        this.setAbleToCalculate(ableToCalculate);
        LOG.debug("isAbleToCalculate: " + this.isAbleToCalculate());
    }

    public void checkAbleToAddLayer() {
        LOG.debug("Checking able to add layer...");
        if (this.layerData.size() > 0) {
            this.setAbleToAddLayer(Float.valueOf(this.layerData.get(this.layerData.size() - 1).getFinalDepth()) <= BoundsEnum.MAX_DEPTH.getPositiveValue());
        }
    }

    public void checkAbleToRemoveLastLayer() {
        LOG.debug("Checking able to remove last layer...");
        boolean ableToRemoveLastLayer = true;
        try {
            if (this.layerData.size() == 0) {
                throw new LiquEcException("No layer to be removed");
            }
            if (this.isLastSptDepthInLastLayerDepth()) {
                throw new LiquEcException("Last SPT depth in last layer depth");
            }
        } catch (LiquEcException e) {
            LOG.debug("Reason: " + e.getMessage());
            ableToRemoveLastLayer = false;
        }
        this.setAbleToRemoveLastLayer(ableToRemoveLastLayer);
        LOG.debug("isAbleToRemoveLastLayer: " + this.isAbleToRemoveLastLayer());
    }

    private boolean isLastSptDepthInLastLayerDepth() {
        if (this.sptData.size() == 0) {
            return false;
        }
        final Float lastSptDepth = Float.valueOf(this.sptData.get(this.sptData.size() - 1).getSptDepth());
        final Float startLastLayerDepth = Float.valueOf(this.layerData.get(this.layerData.size() - 1).getStartDepth());
        final Float finalLastLayerDepth = Float.valueOf(this.layerData.get(this.layerData.size() - 1).getFinalDepth());
        return lastSptDepth > startLastLayerDepth && lastSptDepth <= finalLastLayerDepth;
    }

    public void checkAbleToAddSpt() {
        LOG.debug("Checking able to add spt...");
        this.setAbleToAddSpt(this.layerData.size() > 0);
    }

    public void checkAbleToRemoveLastSpt() {
        LOG.debug("Checking able to remove last SPT...");
        this.setAbleToRemoveLastSpt(this.sptData.size() > 0);
    }

    private String getFormattedDepth(final String depth) {
        if (depth.matches("(\\d)+[\\.]\\d")) {
            return depth + "0";
        }
        return depth;
    }
}
