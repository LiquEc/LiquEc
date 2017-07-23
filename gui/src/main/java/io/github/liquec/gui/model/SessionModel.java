/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.*;
import io.github.liquec.analysis.session.SessionState;
import io.github.liquec.gui.chart.Line;
import io.github.liquec.gui.chart.LiquEcData;
import io.github.liquec.gui.chart.Point;
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
import java.util.List;

public final class SessionModel {
    private static final Logger LOG = LoggerFactory.getLogger(SessionModel.class);

    private static final Integer MAX_DEPTH = 30;

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToCalculate = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty normativeMode = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToAddLayer = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToRemoveLastLayer = new SimpleBooleanProperty(false);
    private final SimpleBooleanProperty ableToAddSpt = new SimpleBooleanProperty(true);
    private final SimpleBooleanProperty ableToRemoveLastSpt = new SimpleBooleanProperty(false);

    private final SimpleStringProperty projectName;
    private final SimpleStringProperty organization;
    private final SimpleStringProperty peakGroundAceleration;
    private final SimpleStringProperty earthquakeMagnitude;
    private final SimpleStringProperty groundWaterTableDepth;

    private final ObservableList<LayerRow> layerData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> layerChartData = FXCollections.observableArrayList();

    private final ObservableList<SptRow> sptData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> sptChartData = FXCollections.observableArrayList();

    public SessionModel(final SessionState state) {
        this.projectName = new SimpleStringProperty(state.getProjectName());
        this.organization = new SimpleStringProperty(state.getOrganization());
        this.peakGroundAceleration = new SimpleStringProperty(state.getSiteConditions().getPeakGroundAceleration() == null ? "" :
            String.valueOf(state.getSiteConditions().getPeakGroundAceleration()));
        this.earthquakeMagnitude = new SimpleStringProperty(state.getSiteConditions().getEarthquakeMagnitude() == null ? "" :
            String.valueOf(state.getSiteConditions().getEarthquakeMagnitude()));
        this.groundWaterTableDepth = new SimpleStringProperty(state.getGeotechnicalProperties().getGroundWaterTableDepth() == null ? "" :
            (String.valueOf(state.getGeotechnicalProperties().getGroundWaterTableDepth())));
        this.initializeLayerData(state.getGeotechnicalProperties().getSoilLayers());
        this.initializeLayerChartData(state.getGeotechnicalProperties().getSoilLayers());
        this.initializeSptData(state.getStandardPenetrationTestList());
        this.initializeSptChartData(state.getStandardPenetrationTestList());
        this.checkAbleToCalculate();
        this.checkAbleToRemoveLastLayer();
        this.checkAbleToRemoveLastSpt();
    }

    private void initializeLayerData(final List<SoilLayer> soilLayers) {
        for (SoilLayer soilLayer : soilLayers) {
            layerData.add(this.buildLayerRow(soilLayer));
        }
    }

    private void initializeLayerChartData(final List<SoilLayer> soilLayers) {
        for (SoilLayer soilLayer : soilLayers) {
            final XYChart.Series<Number, Number> series = new XYChart.Series();
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
            String.valueOf(soilLayer.getStartDepth()),
            String.valueOf(soilLayer.getFinalDepth()),
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
        // spt points
        final XYChart.Series<Number, Number> series = new XYChart.Series();
        series.setName("SPT series");
        for (StandardPenetrationTest standardPenetrationTest : standardPenetrationTestList) {
            series.getData().add(LiquEcData.getChartInverseDataSptPoint(standardPenetrationTest.getSptBlowCounts(), standardPenetrationTest.getDepth()));
        }
        sptChartData.add(series);
        // spt points line
        final XYChart.Series<Number, Number> pointsLine = new XYChart.Series();
        pointsLine.setName("SPT points line");
        sptChartData.add(pointsLine);
        this.drawSptChartPointsLine();
    }

    private SptRow buildSptRow(final StandardPenetrationTest standardPenetrationTest) {
        return new SptRow(
            String.valueOf(standardPenetrationTest.getDepth()),
            String.valueOf(standardPenetrationTest.getSptBlowCounts())
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
        if (this.getSptChartMainDataSeries().getData().size() > 0) {
            this.getSptChartMainDataSeries().getData().remove(this.getSptChartMainDataSeries().getData().size() - 1);
        }
    }

    public XYChart.Series<Number, Number> getSptChartPointsLineSeries() {
        return sptChartData.get(1);
    }

    public void clearSptChartPointsLineSeries() {
        if (this.getSptChartPointsLineSeries().getData().size() > 0) {
            this.getSptChartPointsLineSeries().getData().clear();
        }
    }

    public void drawSptChartPointsLine() {
        this.clearSptChartPointsLineSeries();
        if (this.getSptData().size() <= 1) {
            return;
        }

        for (int i = 0; i <= this.getSptData().size() - 2; i++) {

            final int firstPoint = (Float.valueOf(this.getSptData().get(i).getSptBlowCounts()) < Float.valueOf(this.getSptData().get(i + 1).getSptBlowCounts())) ? i : i + 1;
            final int secondPoint = (firstPoint == i) ? i + 1 : i;
            final float x0 = Float.valueOf(this.getSptData().get(firstPoint).getSptBlowCounts());
            final float y0 = -Float.valueOf(this.getSptData().get(firstPoint).getSptDepth());
            final float x1 = Float.valueOf(this.getSptData().get(secondPoint).getSptBlowCounts());
            final float y1 = -Float.valueOf(this.getSptData().get(secondPoint).getSptDepth());

            Point p0 = new Point(x0, y0);
            Point p1 = new Point(x1, y1);
            Line line = new Line(p0, p1);

            final List<Point> points = line.getPoints();
            for (Point point : points) {
                this.getSptChartPointsLineSeries().getData().add(LiquEcData.getChartDataSptPointLine(point.getX(), point.getY()));
            }
        }
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
        sessionState.setOrganization(this.getOrganization());

        SiteConditions siteConditions = new SiteConditions();
        siteConditions.setPeakGroundAceleration(StringUtils.isEmpty(this.getPeakGroundAceleration()) ? null : Float.valueOf(this.getPeakGroundAceleration()));
        siteConditions.setEarthquakeMagnitude(StringUtils.isEmpty(this.getEarthquakeMagnitude()) ? null : Float.valueOf(this.getEarthquakeMagnitude()));
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
        return standardPenetrationTest;
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
            if (StringUtils.isEmpty(this.getPeakGroundAceleration()) || Float.valueOf(this.getPeakGroundAceleration()) == 0) {
                throw new LiquEcException("peakGroundAceleration");
            }
            LOG.debug("earthquakeMagnitude: " + this.getEarthquakeMagnitude());
            if (StringUtils.isEmpty(this.getEarthquakeMagnitude()) || Float.valueOf(this.getEarthquakeMagnitude()) == 0) {
                throw new LiquEcException("earthquakeMagnitude");
            }
            LOG.debug("groundWaterTableDepth: " + this.getGroundWaterTableDepth());
            if (StringUtils.isEmpty(this.getGroundWaterTableDepth()) || Float.valueOf(this.getGroundWaterTableDepth()) == 0) {
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
            this.setAbleToAddLayer(Float.valueOf(this.layerData.get(this.layerData.size() - 1).getFinalDepth()) <= MAX_DEPTH);
        }
    }

    public void checkAbleToRemoveLastLayer() {
        LOG.debug("Checking able to remove last layer...");
        this.setAbleToRemoveLastLayer(this.layerData.size() > 0);
    }

    public void checkAbleToAddSpt() {
        LOG.debug("Checking able to add spt...");
        if (this.sptData.size() > 0) {
            this.setAbleToAddSpt(Float.valueOf(this.sptData.get(this.sptData.size() - 1).getSptDepth()) <= MAX_DEPTH);
        }
    }

    public void checkAbleToRemoveLastSpt() {
        LOG.debug("Checking able to remove last SPT...");
        this.setAbleToRemoveLastSpt(this.sptData.size() > 0);
    }
}
