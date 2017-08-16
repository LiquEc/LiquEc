/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import com.emxsys.chart.EnhancedLineChart;
import com.emxsys.chart.EnhancedStackedAreaChart;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.gui.model.LayerRow;
import io.github.liquec.gui.model.ResultModel;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.model.SptResultRow;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.collections.ListChangeListener;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    public void initialise(final SessionModel sessionModel, final ResultModel resultModel) {
        this.sessionModel = sessionModel;
        this.resultModel = resultModel;

        // enable calculation button
        this.sessionModel.setAbleToCalculate(false);
        
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
        this.safetyFactorChart.setData(this.resultModel.getCrrChartData());

    }

    private String buildTitle() {
        return "Liquefaction Calculations: " + this.resultModel.getCalculationMode();
    }

    private void back() {
        // enable calculation button
        this.sessionModel.setAbleToCalculate(true);
        this.guiResultHandler.handleReturn();
    }

}
