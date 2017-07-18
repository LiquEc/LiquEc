/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.gui.common.LiquefactionEnum;
import io.github.liquec.gui.model.LayerModel;
import io.github.liquec.gui.model.LayerRow;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
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

    public Button buttonOk;

    public Button buttonCancel;

    private Stage stage;

    private SessionModel sessionModel;

    private LayerModel layerModel;

    @Inject
    private ControllerHelper controllerHelper;

    public void initialise(final Stage stage, final SessionModel sessionModel) {
        this.stage = stage;
        this.sessionModel = sessionModel;
        this.layerModel = new LayerModel(this.retrieveStartDepth());

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

        // Buttons
        buttonOk.setOnAction(e -> saveLayer());
        buttonCancel.setOnAction(e -> exit());

    }

    private String retrieveStartDepth() {
        if (this.sessionModel.getLayerData().size() == 0) {
            return "0.00";
        }
        return this.sessionModel.getLayerData().get(this.sessionModel.getLayerData().size() - 1).getFinalDepth();
    }

    private void buildToggleGroup(final Toggle liquefactionYes, final Toggle liquefactionNo) {
        ToggleGroup normativeGroup = new ToggleGroup();
        liquefactionYes.setToggleGroup(normativeGroup);
        liquefactionNo.setToggleGroup(normativeGroup);
    }

    private void saveLayer() {
        this.sessionModel.getLayerData().add(this.buildLayerRow());
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToRemoveLastLayer();
        this.exit();
    }

    private LayerRow buildLayerRow() {
        return new LayerRow(
            this.layerModel.getStartDepth(),
            this.layerModel.getFinalDepth(),
            this.layerModel.getSoilType(),
            this.layerModel.getSoilUnitWeightAboveGwt(),
            this.layerModel.getSoilUnitWeightBelowGwt(),
            this.layerModel.getFinesContent(),
            LiquefactionEnum.getDescription(this.layerModel.isLayerLiquefaction()));
    }

    private void exit() {
        stage.close();
    }

}
