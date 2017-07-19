/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.analysis.model.SoilLayer;
import io.github.liquec.gui.model.LayerRow;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
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

    private SessionModel sessionModel;

    @Inject
    private ControllerHelper controllerHelper;

    @Inject
    private LayerHandler layerHandler;

    public void initialise(final SessionModel sessionModel) {
        this.sessionModel = sessionModel;

        // Buttons
        removeLastLayerButton.disableProperty().bind(not(this.sessionModel.ableToRemoveLastLayerProperty()));
        removeLastSptButton.disableProperty().bind(not(this.sessionModel.ableToRemoveLastSptProperty()));

        handler(addNewLayerButton, this::processLayerProperties);
        handler(removeLastLayerButton, this::removeLastLayer);

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
        this.textFieldPeakGroundAceleration.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldPeakGroundAceleration,"\\d{0,1}([\\.]\\d{0,2})?", b, c));
        this.textFieldPeakGroundAceleration.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldPeakGroundAceleration, b, c, "00", true));
        // Earthquake Magnitude
        Bindings.bindBidirectional(this.textFieldEarthquakeMagnitude.textProperty(), this.sessionModel.earthquakeMagnitudeProperty());
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Earthquake Magnitude", b, c));
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldEarthquakeMagnitude,"\\d{0,1}([\\.]\\d{0,1})?", b, c));
        this.textFieldEarthquakeMagnitude.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldEarthquakeMagnitude, b, c, "0", true));
        // Ground Water Table Depth
        Bindings.bindBidirectional(this.textFieldGroundWaterTableDepth.textProperty(), this.sessionModel.groundWaterTableDepthProperty());
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Ground Water Table Depth", b, c));
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldGroundWaterTableDepth,"\\d{0,2}([\\.]\\d{0,2})?", b, c));
        this.textFieldGroundWaterTableDepth.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldGroundWaterTableDepth, b, c, "00", true));

        // Layer Table
        startDepthTableColumn.setCellValueFactory(cellData -> cellData.getValue().startDepthProperty());
        finalDepthTableColumn.setCellValueFactory(cellData -> cellData.getValue().finalDepthProperty());
        soilTypeTableColumn.setCellValueFactory(cellData -> cellData.getValue().soilTypeProperty());
        soilUnitWeightAboveGwtTableColumn.setCellValueFactory(cellData -> cellData.getValue().soilUnitWeightAboveGwtProperty());
        soilUnitWeightBelowGwtTableColumn.setCellValueFactory(cellData -> cellData.getValue().soilUnitWeightBelowGwtProperty());
        finesContentTableColumn.setCellValueFactory(cellData -> cellData.getValue().finesContentProperty());
        liquefactionTableColumn.setCellValueFactory(cellData -> cellData.getValue().liquefactionProperty());
        tableLayer.setItems(this.sessionModel.getLayerData());

    }

    private void manageSessionModelState(final String name, final String oldValue, final String newValue) {
        this.controllerHelper.trackValues(name, oldValue, newValue);
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToRemoveLastLayer();
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
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToRemoveLastLayer();
    }

}
