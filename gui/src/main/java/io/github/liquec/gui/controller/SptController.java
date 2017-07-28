/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.chart.LiquEcData;
import io.github.liquec.gui.dialogues.AlertTool;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.model.SptModel;
import io.github.liquec.gui.model.SptRow;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static javafx.beans.binding.Bindings.not;

public class SptController {
    private static final Logger LOG = LoggerFactory.getLogger(SptController.class);

    public TextField textFieldSptDepth;

    public TextField textFieldSptBlowCounts;

    public TextField textFieldSptEnergyRatio;

    public Button buttonOk;

    public Button buttonCancel;

    private Stage stage;

    private SessionModel sessionModel;

    private SptModel sptModel;

    @Inject
    private ControllerHelper controllerHelper;

    public void initialise(final Stage stage, final SessionModel sessionModel) {
        this.stage = stage;
        this.sessionModel = sessionModel;
        this.sptModel = new SptModel();

        // Buttons
        buttonOk.disableProperty().bind(not(this.sptModel.ableToAddProperty()));

        // Depth
        Bindings.bindBidirectional(this.textFieldSptDepth.textProperty(), this.sptModel.depthProperty());
        this.textFieldSptDepth.textProperty().addListener((a, b, c) ->
            this.manageSptModelState("Depth", b, c));
        this.textFieldSptDepth.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldSptDepth,"((1|2)?\\d{0,1}([\\.]\\d{0,2})?)|30|30\\.|30\\.0|30\\.00", b, c));
        this.textFieldSptDepth.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldSptDepth, b, c, "00", true));

        // SPT Blow Counts
        Bindings.bindBidirectional(this.textFieldSptBlowCounts.textProperty(), this.sptModel.sptBlowCountsProperty());
        this.textFieldSptBlowCounts.textProperty().addListener((a, b, c) ->
            this.manageSptModelState("SPT Blow Counts", b, c));
        this.textFieldSptBlowCounts.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldSptBlowCounts,"((1|2|3|4)?\\d{0,1})|50", b, c));
        this.textFieldSptBlowCounts.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldSptBlowCounts, b, c, "0", false));

        // Energy Ratio
        Bindings.bindBidirectional(this.textFieldSptEnergyRatio.textProperty(), this.sptModel.energyRatioProperty());
        this.textFieldSptEnergyRatio.textProperty().addListener((a, b, c) ->
            this.manageSptModelState("Energy Ratio", b, c));
        this.textFieldSptEnergyRatio.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldSptEnergyRatio,"(\\d{0,2}([\\.]\\d{0,1})?)|100|100\\.|100\\.0", b, c));
        this.textFieldSptEnergyRatio.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldSptEnergyRatio, b, c, "0", true));
        this.textFieldSptEnergyRatio.textProperty().setValue("60.0");

        // Buttons
        buttonOk.setOnAction(e -> saveSpt());
        buttonCancel.setOnAction(e -> exit());

    }

    private void manageSptModelState(final String name, final String oldValue, final String newValue) {
        this.controllerHelper.trackValues(name, oldValue, newValue);
        this.sptModel.checkAbleToAdd();
    }

    private void saveSpt() {
        if (!this.checkDuplicatedSpt()) {
            AlertTool.filterErrorAlert(
                "Duplicated SPT",
                "You can't add this SPT.",
                "SPT: depth " + this.textFieldSptDepth.getText() + " (m), blow counts " + this.textFieldSptBlowCounts.getText() + " (N)");
            return;
        }
        this.sessionModel.getSptData().add(this.buildSptRow());
        this.addSptChartData();
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToAddSpt();
        this.sessionModel.checkAbleToRemoveLastSpt();
        this.exit();
    }

    private boolean checkDuplicatedSpt() {
        for (SptRow sptRow : this.sessionModel.getSptData()) {
            if (sptRow.getSptDepth().equals(this.textFieldSptDepth.getText()) && sptRow.getSptBlowCounts().equals(this.textFieldSptBlowCounts.getText())) {
                return false;
            }
        }
        return true;
    }

    private SptRow buildSptRow() {
        return new SptRow(
            this.sptModel.getDepth(),
            this.sptModel.getSptBlowCounts(),
            this.sptModel.getEnergyRatio());
    }

    private void addSptChartData() {
        this.sessionModel.getSptChartMainDataSeries().getData().add(LiquEcData.getChartInverseDataSpt(Integer.valueOf(this.sptModel.getSptBlowCounts()), Float.valueOf(this.sptModel.getDepth())));
    }

    private void exit() {
        stage.close();
    }

}
