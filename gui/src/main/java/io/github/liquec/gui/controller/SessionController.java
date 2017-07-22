/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.gui.model.LayerRow;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.model.SptRow;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static javafx.beans.binding.Bindings.not;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    public TextField textFieldProjectName;

    public TextField textFieldOrganization;

    public TextField textFieldPeakGroundAceleration;

    public TextField textFieldEarthquakeMagnitude;

    public TextField textFieldGroundWaterTableDepth;

    public Button addNewLayerButton;

    public Button removeLastLayerButton;

    public Button addNewSptButton;

    public Button removeLastSptButton;

    public TableView<LayerRow> tableLayer;

    public TableColumn<LayerRow, String> startDepthTableColumn;

    public TableColumn<LayerRow, String> finalDepthTableColumn;

    public TableColumn<LayerRow, String> soilTypeTableColumn;

    public TableColumn<LayerRow, String> soilUnitWeightAboveGwtTableColumn;

    public TableColumn<LayerRow, String> soilUnitWeightBelowGwtTableColumn;

    public TableColumn<LayerRow, String> finesContentTableColumn;

    public TableColumn<LayerRow, String> liquefactionTableColumn;

    public StackedAreaChart<Number, Number> layerStackedAreaChart;

    public NumberAxis xAxisStackedAreaChart;

    public NumberAxis yAxisStackedAreaChart;

    public TableView<SptRow> sptLayer;

    public TableColumn<SptRow, String> sptDepthTableColumn;

    public TableColumn<SptRow, String> sptBlowCountsTableColumn;

    public LineChart<Number, Number> sptLineChart;

    public NumberAxis xAxisLineChart;

    public NumberAxis yAxisLineChart;

    private SessionModel sessionModel;

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private LayerHandler layerHandler;

    @Inject
    private SptHandler sptHandler;

    public void initialise(final SessionModel sessionModel) {
        this.sessionModel = sessionModel;

        // Buttons
        addNewLayerButton.disableProperty().bind(not(this.sessionModel.ableToAddLayerProperty()));
        removeLastLayerButton.disableProperty().bind(not(this.sessionModel.ableToRemoveLastLayerProperty()));
        addNewSptButton.disableProperty().bind(not(this.sessionModel.ableToAddSptProperty()));
        removeLastSptButton.disableProperty().bind(not(this.sessionModel.ableToRemoveLastSptProperty()));

        handler(addNewLayerButton, this::processLayerProperties);
        handler(removeLastLayerButton, this::removeLastLayer);
        handler(addNewSptButton, this::processSptProperties);
        handler(removeLastSptButton, this::removeLastSpt);

        // Normative Mode
        this.sessionModel.normativeModeProperty().addListener((a, b, c) -> this.controllerHelper.trackValues("Normative Mode", b.toString(), c.toString()));
        // Project Name
        Bindings.bindBidirectional(this.textFieldProjectName.textProperty(), this.sessionModel.projectNameProperty());
        this.textFieldProjectName.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Project Name", b, c));
        // Organization
        Bindings.bindBidirectional(this.textFieldOrganization.textProperty(), this.sessionModel.organizationProperty());
        this.textFieldOrganization.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Organization", b, c));
        // Peak Ground Aceleration
        Bindings.bindBidirectional(this.textFieldPeakGroundAceleration.textProperty(), this.sessionModel.peakGroundAcelerationProperty());
        this.textFieldPeakGroundAceleration.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Peak Ground Aceleration", b, c));
        this.textFieldPeakGroundAceleration.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldPeakGroundAceleration,"(\\d{0,1}([\\.]\\d{0,2})?)|10|10\\.|10\\.0|10\\.00", b, c));
        this.textFieldPeakGroundAceleration.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldPeakGroundAceleration, b, c, "00", true));
        // Earthquake Magnitude
        Bindings.bindBidirectional(this.textFieldEarthquakeMagnitude.textProperty(), this.sessionModel.earthquakeMagnitudeProperty());
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Earthquake Magnitude", b, c));
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldEarthquakeMagnitude,"(\\d{0,1}([\\.]\\d{0,1})?)|10|10\\.|10\\.0", b, c));
        this.textFieldEarthquakeMagnitude.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldEarthquakeMagnitude, b, c, "0", true));
        // Ground Water Table Depth
        Bindings.bindBidirectional(this.textFieldGroundWaterTableDepth.textProperty(), this.sessionModel.groundWaterTableDepthProperty());
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Ground Water Table Depth", b, c));
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldGroundWaterTableDepth,"(\\d{0,2}([\\.]\\d{0,2})?)|100|100\\.|100\\.0|100\\.00", b, c));
        this.textFieldGroundWaterTableDepth.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldGroundWaterTableDepth, b, c, "00", true));

        // Layer Table
        this.startDepthTableColumn.setCellValueFactory(cellData -> cellData.getValue().startDepthProperty());
        this.finalDepthTableColumn.setCellValueFactory(cellData -> cellData.getValue().finalDepthProperty());
        this.soilTypeTableColumn.setCellValueFactory(cellData -> cellData.getValue().soilTypeProperty());
        this.soilUnitWeightAboveGwtTableColumn.setCellValueFactory(cellData -> cellData.getValue().soilUnitWeightAboveGwtProperty());
        this.soilUnitWeightBelowGwtTableColumn.setCellValueFactory(cellData -> cellData.getValue().soilUnitWeightBelowGwtProperty());
        this.finesContentTableColumn.setCellValueFactory(cellData -> cellData.getValue().finesContentProperty());
        this.liquefactionTableColumn.setCellValueFactory(cellData -> cellData.getValue().liquefactionProperty());
        this.tableLayer.setItems(this.sessionModel.getLayerData());

        // Area Chart
        this.yAxisStackedAreaChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisStackedAreaChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.layerStackedAreaChart.setData(this.sessionModel.getLayerChartData());
        this.sessionModel.getLayerChartData().addListener((ListChangeListener.Change<? extends XYChart.Series<Number, Number>> c) -> this.manageStackedAreaChartAutoRanging());
        this.manageStackedAreaChartAutoRanging();

        // Spt Table
        this.sptDepthTableColumn.setCellValueFactory(cellData -> cellData.getValue().sptDepthProperty());
        this.sptBlowCountsTableColumn.setCellValueFactory(cellData -> cellData.getValue().sptBlowCountsProperty());
        this.sptLayer.setItems(this.sessionModel.getSptData());

        // Line Chart
        this.yAxisLineChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisLineChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.sptLineChart.setData(this.sessionModel.getSptChartData());
        this.sessionModel.getSptChartDataSeries().getData().addListener((ListChangeListener.Change<? extends XYChart.Data<Number, Number>> c) -> this.manageLineCharAutoRanging());
        this.manageLineCharAutoRanging();
    }

    private void manageSessionModelState(final String name, final String oldValue, final String newValue) {
        this.controllerHelper.trackValues(name, oldValue, newValue);
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToAddLayer();
        this.sessionModel.checkAbleToRemoveLastLayer();
        this.sessionModel.checkAbleToAddSpt();
        this.sessionModel.checkAbleToRemoveLastSpt();
    }

    private void handler(final Button button, final Runnable action) {
        EventHandler<ActionEvent> handler = e -> action.run();
        button.setOnAction(handler);
    }

    private void processLayerProperties() {
        layerHandler.show(this.sessionModel);
    }

    private void removeLastLayer() {
        this.sessionModel.removeLastLayer();
        this.sessionModel.removeLastChartLayer();
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToAddLayer();
        this.sessionModel.checkAbleToRemoveLastLayer();
    }

    private void processSptProperties() {
        sptHandler.show(this.sessionModel);
    }

    private void removeLastSpt() {
        this.sessionModel.removeLastSpt();
        this.sessionModel.removeLastChartSpt();
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToAddSpt();
        this.sessionModel.checkAbleToRemoveLastSpt();
    }

    private void manageStackedAreaChartAutoRanging() {
        if (!this.yAxisStackedAreaChart.isAutoRanging()) {
            this.yAxisStackedAreaChart.setAutoRanging(this.sessionModel.getLayerChartData().size() > 0);
        }
    }

    private void manageLineCharAutoRanging() {
        if (!this.yAxisLineChart.isAutoRanging()) {
            this.yAxisLineChart.setAutoRanging(this.sessionModel.getSptChartDataSeries().getData().size() > 0);
        }
    }

}
