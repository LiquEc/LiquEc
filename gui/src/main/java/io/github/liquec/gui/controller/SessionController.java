/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import com.emxsys.chart.EnhancedLineChart;
import com.emxsys.chart.EnhancedStackedAreaChart;
import com.emxsys.chart.extension.ValueMarker;
import io.github.liquec.gui.common.BoundsEnum;
import io.github.liquec.gui.model.LayerRow;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.model.SptRow;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static javafx.beans.binding.Bindings.not;

public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    public TextField textFieldProjectName;

    public TextField textFieldProjectLocation;

    public TextField textFieldOrganization;

    public TextField textFieldPeakGroundAcceleration;

    public TextField textFieldEarthquakeMagnitude;

    public TextField textFieldCoefficientOfContribution;

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

    public EnhancedStackedAreaChart<Number, Number> layerStackedAreaChart;

    public NumberAxis xAxisStackedAreaChart;

    public NumberAxis yAxisStackedAreaChart;

    public TableView<SptRow> sptLayer;

    public TableColumn<SptRow, String> sptDepthTableColumn;

    public TableColumn<SptRow, String> sptBlowCountsTableColumn;

    public EnhancedLineChart<Number, Number> sptLineChart;

    public NumberAxis xAxisLineChart;

    public NumberAxis yAxisLineChart;

    public AnchorPane graphicLayerPane;

    public AnchorPane graphicStpPane;

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
        this.sessionModel.normativeModeProperty().addListener((a, b, c) -> this.manageNormativeMode(b, c));
        // Project Name
        Bindings.bindBidirectional(this.textFieldProjectName.textProperty(), this.sessionModel.projectNameProperty());
        this.textFieldProjectName.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Project Name", b, c));
        // Project Location
        Bindings.bindBidirectional(this.textFieldProjectLocation.textProperty(), this.sessionModel.projectLocationProperty());
        this.textFieldProjectLocation.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Project Location", b, c));
        // Organization
        Bindings.bindBidirectional(this.textFieldOrganization.textProperty(), this.sessionModel.organizationProperty());
        this.textFieldOrganization.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Organization", b, c));
        // Peak Ground Acceleration
        Bindings.bindBidirectional(this.textFieldPeakGroundAcceleration.textProperty(), this.sessionModel.peakGroundAccelerationProperty());
        this.textFieldPeakGroundAcceleration.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Peak Ground Acceleration", b, c));
        this.textFieldPeakGroundAcceleration.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldPeakGroundAcceleration,"(\\d{0,1}([\\.]\\d{0,2})?)|10|10\\.|10\\.0|10\\.00", b, c));
        this.textFieldPeakGroundAcceleration.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldPeakGroundAcceleration, b, c, "00", true));
        // Earthquake Magnitude
        Bindings.bindBidirectional(this.textFieldEarthquakeMagnitude.textProperty(), this.sessionModel.earthquakeMagnitudeProperty());
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Earthquake Magnitude", b, c));
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldEarthquakeMagnitude,"(\\d{0,1}([\\.]\\d{0,1})?)|10|10\\.|10\\.0", b, c));
        this.textFieldEarthquakeMagnitude.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldEarthquakeMagnitude, b, c, "0", true));
        // Coefficient of Contribution
        Bindings.bindBidirectional(this.textFieldCoefficientOfContribution.textProperty(), this.sessionModel.coefficientOfContributionProperty());
        this.textFieldCoefficientOfContribution.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Coefficient of Contribution", b, c));
        this.textFieldCoefficientOfContribution.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldCoefficientOfContribution,"([1]([\\.]\\d{0,1})?)|2|2\\.|2\\.0", b, c));
        this.textFieldCoefficientOfContribution.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldCoefficientOfContribution, b, c, "0", true));
        // Ground Water Table Depth
        Bindings.bindBidirectional(this.textFieldGroundWaterTableDepth.textProperty(), this.sessionModel.groundWaterTableDepthProperty());
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) ->
            this.manageSessionModelState("Ground Water Table Depth", b, c));
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldGroundWaterTableDepth,"((1|2)?\\d{0,1}([\\.]\\d{0,2})?)|30|30\\.|30\\.0|30\\.00", b, c));
        this.textFieldGroundWaterTableDepth.focusedProperty().addListener((a, b, c) ->
            this.manageGroundWaterTableDepth(this.textFieldGroundWaterTableDepth, b, c, "00", true));

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
        this.xAxisStackedAreaChart.setMinorTickVisible(false);
        this.xAxisStackedAreaChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.xAxisStackedAreaChart) {
            @Override
            public String toString(final Number value) {
                return "";
            }
        });
        this.yAxisStackedAreaChart.setTickLabelFormatter(new NumberAxis.DefaultFormatter(this.yAxisStackedAreaChart) {
            @Override
            public String toString(final Number value) {
                return (value.doubleValue() == 0) ? value.toString() : String.format("%7.1f", -value.doubleValue());
            }
        });
        this.layerStackedAreaChart.setAnimated(false);
        this.layerStackedAreaChart.setLegendVisible(false);
        this.layerStackedAreaChart.setData(this.sessionModel.getLayerChartData());
        this.sessionModel.getLayerChartData().addListener((ListChangeListener.Change<? extends XYChart.Series<Number, Number>> c) -> this.manageChartsAutoRangingAndExtendedFeatures());

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
        this.sptLineChart.setAnimated(false);
        this.sptLineChart.setLegendVisible(false);
        this.sptLineChart.setAxisSortingPolicy(LineChart.SortingPolicy.Y_AXIS);
        this.sptLineChart.setData(this.sessionModel.getSptChartData());
        this.sessionModel.getSptChartMainDataSeries().getData().addListener((ListChangeListener.Change<? extends XYChart.Data<Number, Number>> c) -> this.manageChartsAutoRangingAndExtendedFeatures());
        this.manageSptLineChartTooltip();

        // Initialize Auto Ranging and Extended Features
        this.manageChartsAutoRangingAndExtendedFeatures();

        // Initialize Water Makers
        this.manageWaterDepthMarker();
    }

    private void manageNormativeMode(final Boolean b, final Boolean c) {
        this.controllerHelper.trackValues("Normative Mode", b.toString(), c.toString());
        this.sessionModel.checkAbleToCalculate();
    }

    private String searchSptEnergyRatio(final Number yValue, final Number xValue) {
        for (SptRow sptRow : this.sessionModel.getSptData()) {
            if (Float.valueOf(sptRow.getSptDepth()).equals(-yValue.floatValue()) && Float.valueOf(sptRow.getSptBlowCounts()).equals(xValue.floatValue())) {
                return sptRow.getSptEnergyRatio();
            }
        }
        return "";
    }

    private void manageGroundWaterTableDepth(final TextField textField, final Boolean b, final Boolean c, final String zeros, final boolean remove) {
        this.controllerHelper.manageZerosValues(this.textFieldGroundWaterTableDepth, b, c, "00", true);
        this.manageWaterDepthMarker();
    }


    private void manageWaterDepthMarker() {
        this.layerStackedAreaChart.getMarkers().clearRangeMarkers();
        this.sptLineChart.getMarkers().clearRangeMarkers();
        if (!StringUtils.isEmpty(this.sessionModel.getGroundWaterTableDepth())) {
            this.layerStackedAreaChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
            this.sptLineChart.getMarkers().addRangeMarker(new ValueMarker(-Float.valueOf(this.sessionModel.getGroundWaterTableDepth()), "GWT", Pos.BOTTOM_RIGHT));
        }
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
        this.sessionModel.checkAbleToAddSpt();
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
        this.sessionModel.checkAbleToRemoveLastLayer();
    }

    private void manageChartsAutoRangingAndExtendedFeatures() {
        // stacked area chart
        this.yAxisStackedAreaChart.setLowerBound(this.lowerBoundAxisY());
        this.yAxisStackedAreaChart.setTickUnit(this.tickUnitAxisY());
        // line chart
        this.xAxisLineChart.setUpperBound(this.upperBoundAxisX());
        this.xAxisLineChart.setTickUnit(this.tickUnitAxisX());
        this.yAxisLineChart.setLowerBound(this.lowerBoundAxisY());
        this.yAxisLineChart.setTickUnit(this.tickUnitAxisY());
        // extended features
        this.manageSptLineChartTooltip();
        this.manageWaterDepthMarker();
    }

    private Double upperBoundAxisX() {
        return this.getPairValueAxisX((this.sessionModel.getSptData().size() > 0)
            ? Math.ceil(this.searchMaxSptBlowCounts() + 1) : BoundsEnum.MAX_SPT.getPositiveValue());
    }

    private Double getPairValueAxisX(final Double value) {
        return (value % 2 == 0) ? value : value + 1;
    }

    private Double searchMaxSptBlowCounts() {
        Double max = 0.0;
        for (SptRow sptRow : this.sessionModel.getSptData()) {
            if (Double.valueOf(sptRow.getSptBlowCounts()) > max) {
                max = Double.valueOf(sptRow.getSptBlowCounts());
            }
        }
        return max;
    }

    private Double lowerBoundAxisY() {
        final Double layerLowerBound = this.getPairValueAxisY((this.sessionModel.getLayerData().size() > 0)
            ? -Math.ceil(Double.valueOf(this.sessionModel.getLayerData().get(this.sessionModel.getLayerData().size() - 1).getFinalDepth()) + 1) : BoundsEnum.MAX_DEPTH.getNegativeValue());
        final Double sptLowerBound = this.getPairValueAxisY((this.sessionModel.getSptData().size() > 0)
            ? -Math.ceil(Double.valueOf(this.sessionModel.getSptData().get(this.sessionModel.getSptData().size() - 1).getSptDepth()) + 1) : BoundsEnum.MAX_DEPTH.getNegativeValue());
        if (this.sessionModel.getLayerData().size() == 0) {
            return sptLowerBound;
        }
        if (this.sessionModel.getSptData().size() == 0) {
            return layerLowerBound;
        }
        if (layerLowerBound < sptLowerBound) {
            return layerLowerBound;
        }
        return sptLowerBound;
    }

    private Double getPairValueAxisY(final Double value) {
        return (value % 2 == 0) ? value : value - 1;
    }

    private Double tickUnitAxisX() {
        if (this.upperBoundAxisX() <= 5) {
            return 1.0;
        }
        if (this.upperBoundAxisX() <= 10) {
            return 2.0;
        }
        if (this.upperBoundAxisX() <= 30) {
            return 5.0;
        }
        return 10.0;
    }

    private Double tickUnitAxisY() {
        if (this.lowerBoundAxisY() >= -10) {
            return 1.0;
        }
        if (this.lowerBoundAxisY() >= -20) {
            return 2.0;
        }
        return 5.0;
    }

    private void manageSptLineChartTooltip() {
        for (XYChart.Data<Number, Number> d : sptLineChart.getData().get(0).getData()) {
            Tooltip.install(d.getNode(), null); // remove
            Tooltip.install(d.getNode(), new Tooltip("Blow Counts (N): " + d.getXValue() + "\n" + "Energy Ratio (%): " + this.searchSptEnergyRatio(d.getYValue(), d.getXValue())));
            // Adding class on hover
            d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));
            // Removing class on exit
            d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
        }
    }

}
