/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.gui.model.LayerModel;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class LayerController {
    private static final Logger LOG = LoggerFactory.getLogger(LayerController.class);

    public TextField textFieldStartDepth;

    public TextField textFieldFinalDepth;

    public TextField textFieldSoilType;

    public TextField textFieldSoilUnitWeightAboveGwt;

    public TextField textFieldSoilUnitWeightBelowGwt;

    public TextField textFieldFinesContent;

    public RadioButton liquefactionYesRadioButton;

    public RadioButton liquefactionNoRadioButton;

    private Stage stage;

    private LayerModel layerModel;

    @Inject
    private ControllerHelper controllerHelper;

    public void initialise(final Stage stage, final String startDepth) {
        this.stage = stage;
        this.layerModel = new LayerModel(startDepth);

        buildToggleGroup(liquefactionYesRadioButton, liquefactionNoRadioButton);

        // Start Depth
        Bindings.bindBidirectional(this.textFieldStartDepth.textProperty(), this.layerModel.startDepthProperty());

        // Final Depth
        Bindings.bindBidirectional(this.textFieldFinalDepth.textProperty(), this.layerModel.finalDepthProperty());

        // Soil Type
        Bindings.bindBidirectional(this.textFieldSoilType.textProperty(), this.layerModel.soilTypeProperty());

        // Soil Unit Weight Above Gwt
        Bindings.bindBidirectional(this.textFieldSoilUnitWeightAboveGwt.textProperty(), this.layerModel.soilUnitWeightAboveGwtProperty());

        // Soil Unit Weight Below Gwt
        Bindings.bindBidirectional(this.textFieldSoilUnitWeightBelowGwt.textProperty(), this.layerModel.soilUnitWeightBelowGwtProperty());

        // Fines Content
        Bindings.bindBidirectional(this.textFieldFinesContent.textProperty(), this.layerModel.finesContentProperty());

        // Liquefaction
        liquefactionYesRadioButton.selectedProperty().bindBidirectional(layerModel.layerLiquefactionProperty());
        this.layerModel.layerLiquefactionProperty().addListener((a, b, c) -> this.controllerHelper.trackValues("Layer Liquefaction", b.toString(), c.toString()));

    }

    private void buildToggleGroup(final Toggle liquefactionYes, final Toggle liquefactionNo) {
        ToggleGroup normativeGroup = new ToggleGroup();
        liquefactionYes.setToggleGroup(normativeGroup);
        liquefactionNo.setToggleGroup(normativeGroup);
    }

}
