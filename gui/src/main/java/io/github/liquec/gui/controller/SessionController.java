/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static javafx.beans.binding.Bindings.not;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    public GridPane basicDataGridPane;

    public GridPane informationGridPane;

    public GridPane siteConditionsGridPane;

    public TextField textFieldProjectName;

    public TextField textFieldOrganization;

    public TextField textFieldPeakGroundAceleration;

    public TextField textFieldEarthquakeMagnitude;

    public TextField textFieldGroundWaterTableDepth;

    public Button removeLastLayerButton;

    public Button removeLastSptButton;

    private SessionModel sessionModel;

    @Inject
    private ControllerHelper controllerHelper;

    public void initialise(final GuiTaskHandler guiTaskHandler, final SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        // Buttons
        removeLastLayerButton.disableProperty().bind(not(this.sessionModel.ableToRemoveLastLayerProperty()));
        removeLastSptButton.disableProperty().bind(not(this.sessionModel.ableToRemoveLastSptProperty()));
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
        this.textFieldPeakGroundAceleration.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldPeakGroundAceleration, b, c, "00"));
        // Earthquake Magnitude
        Bindings.bindBidirectional(this.textFieldEarthquakeMagnitude.textProperty(), this.sessionModel.earthquakeMagnitudeProperty());
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Earthquake Magnitude", b, c));
        this.textFieldEarthquakeMagnitude.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldEarthquakeMagnitude,"\\d{0,1}([\\.]\\d{0,1})?", b, c));
        this.textFieldEarthquakeMagnitude.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldEarthquakeMagnitude, b, c, "0"));
        // Ground Water Table Depth
        Bindings.bindBidirectional(this.textFieldGroundWaterTableDepth.textProperty(), this.sessionModel.groundWaterTableDepthProperty());
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.manageSessionModelState("Ground Water Table Depth", b, c));
        this.textFieldGroundWaterTableDepth.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldGroundWaterTableDepth,"\\d{0,2}([\\.]\\d{0,2})?", b, c));
        this.textFieldGroundWaterTableDepth.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldGroundWaterTableDepth, b, c, "00"));
        // Layer Table

    }

    private void manageSessionModelState(final String name, final String oldValue, final String newValue) {
        this.controllerHelper.trackValues(name, oldValue, newValue);
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToRemoveLastLayer();
        this.sessionModel.checkAbleToRemoveLastSpt();
    }

}
