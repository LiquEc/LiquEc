/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.model.SoilLayer;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.gui.chart.LiquEcData;
import io.github.liquec.gui.common.LiquefactionEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public final class ResultModel {
    private static final Logger LOG = LoggerFactory.getLogger(ResultModel.class);
    private static final String DASH = "-";

    private final String calculationMode;

    private final ObservableList<SptResultRow> sptResultData = FXCollections.observableArrayList();

    private final ObservableList<XYChart.Series<Number, Number>> soilProfileChartData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> sptChartData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> csrChartData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> crrChartData = FXCollections.observableArrayList();
    private final ObservableList<XYChart.Series<Number, Number>> safetyFactorChartData = FXCollections.observableArrayList();

    public ResultModel(final ResultState state) {
        this.calculationMode = state.getCalculationMode();
        this.initializeSptResultData(state.getSptCalculationResultList());
        this.initializeLayerChartData(state.getGeotechnicalProperties().getSoilLayers());
        this.initializeResultChartsData(state.getSptCalculationResultList());
    }

    public String getCalculationMode() {
        return calculationMode;
    }

    public ObservableList<SptResultRow> getSptResultData() {
        return sptResultData;
    }

    public ObservableList<XYChart.Series<Number, Number>> getSoilProfileChartData() {
        return soilProfileChartData;
    }

    public ObservableList<XYChart.Series<Number, Number>> getSptChartData() {
        return sptChartData;
    }

    public ObservableList<XYChart.Series<Number, Number>> getCsrChartData() {
        return csrChartData;
    }

    public ObservableList<XYChart.Series<Number, Number>> getCrrChartData() {
        return crrChartData;
    }

    public ObservableList<XYChart.Series<Number, Number>> getSafetyFactorChartData() {
        return safetyFactorChartData;
    }

    private void initializeSptResultData(List<SptCalculationResult> sptCalculationResultList) {
        for (SptCalculationResult sptCalculationResult : sptCalculationResultList) {
            this.sptResultData.add(this.buildSptResultRow(sptCalculationResult));
        }
    }

    private SptResultRow buildSptResultRow(final SptCalculationResult sptCalculationResult) {
        if (sptCalculationResult.getResult()) {
            return new SptResultRow(
                sptCalculationResult.getResult(),
                this.getFormattedDepth(String.valueOf(sptCalculationResult.getDepth())),
                String.valueOf(sptCalculationResult.getSptBlowCounts()),
                String.valueOf(this.round(sptCalculationResult.getSptCorrected())),
                String.valueOf(this.round(sptCalculationResult.getCycleStressRatio())),
                String.valueOf(this.round(sptCalculationResult.getCycleResistanceRatioCorrected())),
                String.valueOf(this.round(sptCalculationResult.getSafetyFactor())));
        }
        return new SptResultRow(
            sptCalculationResult.getResult(),
            this.getFormattedDepth(String.valueOf(sptCalculationResult.getDepth())),
            String.valueOf(sptCalculationResult.getSptBlowCounts()),
            DASH,
            DASH,
            DASH,
            sptCalculationResult.getErrorMessage());
    }

    private String getFormattedDepth(final String depth) {
        if (depth.matches("(\\d)+[\\.]\\d")) {
            return depth + "0";
        }
        return depth;
    }

    private double round(final Double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    private void initializeLayerChartData(final List<SoilLayer> soilLayers) {
        for (SoilLayer soilLayer : soilLayers) {
            final XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
            series.setName(soilLayer.getSoilType());
            final ObservableList<XYChart.Data<Number, Number>> data = FXCollections.observableArrayList();
            data.add(LiquEcData.getChartInverseDataLowerX(soilLayer.getFinalDepth() - soilLayer.getStartDepth()));
            data.add(LiquEcData.getChartInverseDataUpperX(soilLayer.getFinalDepth() - soilLayer.getStartDepth()));
            series.setData(data);
            soilProfileChartData.add(series);
        }
    }

    private void initializeResultChartsData(List<SptCalculationResult> sptCalculationResultList) {
        final XYChart.Series<Number, Number> sptCorrectedSeries = new XYChart.Series<Number, Number>();
        sptCorrectedSeries.setName("SPT series");
        final XYChart.Series<Number, Number> csrSeries = new XYChart.Series<Number, Number>();
        csrSeries.setName("CSR series");
        final XYChart.Series<Number, Number> crrSeries = new XYChart.Series<Number, Number>();
        crrSeries.setName("CRR series");
        final XYChart.Series<Number, Number> safetyFactorSeries = new XYChart.Series<Number, Number>();
        safetyFactorSeries.setName("Safety factor series");
        for (SptCalculationResult sptCalculationResult : sptCalculationResultList) {
            if(sptCalculationResult.getResult()) {
                sptCorrectedSeries.getData().add(LiquEcData.getChartInverseDataSpt(sptCalculationResult.getSptCorrected(), sptCalculationResult.getDepth()));
                csrSeries.getData().add(LiquEcData.getChartInverseDataSpt(sptCalculationResult.getCycleStressRatio(), sptCalculationResult.getDepth()));
                crrSeries.getData().add(LiquEcData.getChartInverseDataSpt(sptCalculationResult.getCycleResistanceRatioCorrected(), sptCalculationResult.getDepth()));
                safetyFactorSeries.getData().add(LiquEcData.getChartInverseDataSpt(sptCalculationResult.getSafetyFactor(), sptCalculationResult.getDepth()));
            }
        }
        sptChartData.add(sptCorrectedSeries);
        csrChartData.add(csrSeries);
        crrChartData.add(crrSeries);
        safetyFactorChartData.add(safetyFactorSeries);
    }
}
