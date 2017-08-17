/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import com.emxsys.chart.EnhancedLineChart;
import com.emxsys.chart.EnhancedStackedAreaChart;
import com.emxsys.chart.extension.ValueMarker;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.gui.common.BoundsEnum;
import io.github.liquec.gui.model.*;
import io.github.liquec.gui.services.ChartHelper;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.collections.ListChangeListener;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ResultController {
    private static final Logger LOG = LoggerFactory.getLogger(ResultController.class);

    public Label resultLabel;

    public Button buttonReturn;

    private SessionModel sessionModel;

    private ResultModel resultModel;

    public TableView<SptResultRow> resultTable;

    public TableColumn<SptResultRow, String> depthTableColumn;

    public TableColumn<SptResultRow, String> sptBlowCountsTableColumn;

    public TableColumn<SptResultRow, String> sptCorrectedTableColumn;

    public TableColumn<SptResultRow, String> csrTableColumn;

    public TableColumn<SptResultRow, String> crrTableColumn;

    public TableColumn<SptResultRow, String> safetyFactorTableColumn;

    public EnhancedStackedAreaChart<Number, Number> soilProfileChart;

    public NumberAxis xAxisSoilProfileChart;

    public NumberAxis yAxisSoilProfileChart;

    public EnhancedLineChart<Number, Number> sptChart;

    public NumberAxis xAxisSptChart;

    public NumberAxis yAxisSptChart;

    public EnhancedLineChart<Number, Number> csrChart;

    public NumberAxis xAxisCsrChart;

    public NumberAxis yAxisCsrChart;

    public EnhancedLineChart<Number, Number> crrChart;

    public NumberAxis xAxisCrrChart;

    public NumberAxis yAxisCrrChart;

    public EnhancedLineChart<Number, Number> safetyFactorChart;

    public NumberAxis xAxisSafetyFactorChart;

    public NumberAxis yAxisSafetyFactorChart;

    @Inject
    private GuiResultHandler guiResultHandler;

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private ChartHelper chartHelper;

    public void initialise(final SessionModel sessionModel, final ResultModel resultModel) {
        this.sessionModel = sessionModel;
        this.resultModel = resultModel;

        // enable calculation button
        this.sessionModel.setResultOpen(true);
        
        this.resultLabel.setText(this.buildTitle());

        // Buttons
        buttonReturn.setOnAction(e -> back());

        // Table
        this.depthTableColumn.setCellValueFactory(cellData -> cellData.getValue().depthProperty());
        this.sptBlowCountsTableColumn.setCellValueFactory(cellData -> cellData.getValue().sptBlowCountsProperty());
        this.sptCorrectedTableColumn.setCellValueFactory(cellData -> cellData.getValue().sptCorrectedProperty());
        this.csrTableColumn.setCellValueFactory(cellData -> cellData.getValue().csrProperty());
        this.crrTableColumn.setCellValueFactory(cellData -> cellData.getValue().crrProperty());
        this.safetyFactorTableColumn.setCellValueFactory(cellData -> cellData.getValue().safetyFactorProperty());
        this.resultTable.setItems(this.resultModel.getSptResultData());

        // Area Chart
        this.xAxisSoilProfileChart.setMinorTickVisible(false);
        this.xAxisSoilProfileChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.xAxisSoilProfileChart) {
            @Override
            public String toString(final Number value) {
                return "";
            }
        });
        this.yAxisSoilProfileChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisSoilProfileChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.soilProfileChart.setAnimated(false);
        this.soilProfileChart.setLegendVisible(false);
        this.soilProfileChart.setData(this.resultModel.getSoilProfileChartData());

        // SPT Chart
        this.yAxisSptChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisSptChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.sptChart.setAnimated(false);
        this.sptChart.setLegendVisible(false);
        this.sptChart.setAxisSortingPolicy(LineChart.SortingPolicy.Y_AXIS);
        this.sptChart.setData(this.resultModel.getSptChartData());

        // CSR Chart
        this.yAxisCsrChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisCsrChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.csrChart.setAnimated(false);
        this.csrChart.setLegendVisible(false);
        this.csrChart.setAxisSortingPolicy(LineChart.SortingPolicy.Y_AXIS);
        this.csrChart.setData(this.resultModel.getCsrChartData());

        // CCR Chart
        this.yAxisCrrChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisCrrChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.crrChart.setAnimated(false);
        this.crrChart.setLegendVisible(false);
        this.crrChart.setAxisSortingPolicy(LineChart.SortingPolicy.Y_AXIS);
        this.crrChart.setData(this.resultModel.getCrrChartData());

        // Safety Factor Chart
        this.yAxisSafetyFactorChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisSafetyFactorChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.safetyFactorChart.setAnimated(false);
        this.safetyFactorChart.setLegendVisible(false);
        this.safetyFactorChart.setAxisSortingPolicy(LineChart.SortingPolicy.Y_AXIS);
        this.safetyFactorChart.setData(this.resultModel.getSafetyFactorChartData());

        this.manageWaterDepthMarker();
        this.manageChartsAutoRanging();

    }

    private String buildTitle() {
        return "Liquefaction Calculations: " + this.resultModel.getCalculationMode();
    }

    private void back() {
        this.sessionModel.setResultOpen(false);
        this.guiResultHandler.handleReturn();
    }

    private void manageWaterDepthMarker() {
        this.soilProfileChart.getMarkers().clearRangeMarkers();
        this.sptChart.getMarkers().clearRangeMarkers();
        this.csrChart.getMarkers().clearRangeMarkers();
        this.crrChart.getMarkers().clearRangeMarkers();
        this.safetyFactorChart.getMarkers().clearRangeMarkers();
        if (!StringUtils.isEmpty(this.sessionModel.getGroundWaterTableDepth())) {
            this.soilProfileChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
            this.sptChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
            this.csrChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
            this.crrChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
            this.safetyFactorChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
        }
    }

    private void manageChartsAutoRanging() {
        // soil profile chart
        this.yAxisSoilProfileChart.setLowerBound(this.chartHelper.lowerBoundAxisY(this.sessionModel));
        this.yAxisSoilProfileChart.setTickUnit(this.chartHelper.tickUnitAxisY(this.chartHelper.lowerBoundAxisY(this.sessionModel)));
        // spt corrected chart
        this.xAxisSptChart.setUpperBound(this.upperBoundSptAxisX());
        this.xAxisSptChart.setTickUnit(this.chartHelper.tickUnitAxisX(this.upperBoundSptAxisX()));
        this.yAxisSptChart.setLowerBound(this.chartHelper.lowerBoundAxisY(this.sessionModel));
        this.yAxisSptChart.setTickUnit(this.chartHelper.tickUnitAxisY(this.chartHelper.lowerBoundAxisY(this.sessionModel)));
        // csr chart
        this.xAxisCsrChart.setUpperBound(this.upperBoundCsrAxisX());
        this.xAxisCsrChart.setTickUnit(0.2);
        this.yAxisCsrChart.setLowerBound(this.chartHelper.lowerBoundAxisY(this.sessionModel));
        this.yAxisCsrChart.setTickUnit(this.chartHelper.tickUnitAxisY(this.chartHelper.lowerBoundAxisY(this.sessionModel)));
        // crr chart
        this.xAxisCrrChart.setUpperBound(this.upperBoundCrrAxisX());
        this.xAxisCrrChart.setTickUnit(0.2);
        this.yAxisCrrChart.setLowerBound(this.chartHelper.lowerBoundAxisY(this.sessionModel));
        this.yAxisCrrChart.setTickUnit(this.chartHelper.tickUnitAxisY(this.chartHelper.lowerBoundAxisY(this.sessionModel)));
        // safety factor chart
        this.xAxisSafetyFactorChart.setUpperBound(this.upperBoundSafetyFactorAxisX());
        this.xAxisSafetyFactorChart.setTickUnit(2);
        this.yAxisSafetyFactorChart.setLowerBound(this.chartHelper.lowerBoundAxisY(this.sessionModel));
        this.yAxisSafetyFactorChart.setTickUnit(this.chartHelper.tickUnitAxisY(this.chartHelper.lowerBoundAxisY(this.sessionModel)));
    }

    private Double upperBoundSptAxisX() {
        return this.chartHelper.getPairValueAxisX((this.resultModel.getSptResultData().size() > 0)
            ? Math.ceil(this.searchMaxSptBlowCounts() + 1) : BoundsEnum.MAX_SPT.getPositiveValue());
    }

    private Double searchMaxSptBlowCounts() {
        Double max = 0.0;
        for (SptResultRow sptResultRow : this.resultModel.getSptResultData()) {
            if (sptResultRow.isResult() && Double.valueOf(sptResultRow.getSptCorrected()) > max) {
                max = Double.valueOf(sptResultRow.getSptCorrected());
            }
        }
        return max;
    }

    private Double upperBoundCsrAxisX() {
        return (this.resultModel.getSptResultData().size() > 0) ? (this.searchMaxCsr() + 0.2) : BoundsEnum.MAX_CSR.getPositiveValue();
    }

    private Double searchMaxCsr() {
        Double max = 0.0;
        for (SptResultRow sptResultRow : this.resultModel.getSptResultData()) {
            if (sptResultRow.isResult() && Double.valueOf(sptResultRow.getCsr()) > max) {
                max = Double.valueOf(sptResultRow.getCsr());
            }
        }
        return max;
    }

    private Double upperBoundCrrAxisX() {
        return (this.resultModel.getSptResultData().size() > 0) ? (this.searchMaxCrr() + 0.2) : BoundsEnum.MAX_CRR.getPositiveValue();
    }

    private Double searchMaxCrr() {
        Double max = 0.0;
        for (SptResultRow sptResultRow : this.resultModel.getSptResultData()) {
            if (sptResultRow.isResult() && Double.valueOf(sptResultRow.getCrr()) > max) {
                max = Double.valueOf(sptResultRow.getCrr());
            }
        }
        return max;
    }

    private Double upperBoundSafetyFactorAxisX() {
        return (this.resultModel.getSptResultData().size() > 0) ? (this.searchMaxSafetyFactor() + 0.6) : BoundsEnum.MAX_SAFETY_FACTOR.getPositiveValue();
    }

    private Double searchMaxSafetyFactor() {
        Double max = 0.0;
        for (SptResultRow sptResultRow : this.resultModel.getSptResultData()) {
            if (sptResultRow.isResult() && Double.valueOf(sptResultRow.getSafetyFactor()) > max) {
                max = Double.valueOf(sptResultRow.getSafetyFactor());
            }
        }
        return max;
    }

}
