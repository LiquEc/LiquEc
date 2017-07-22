/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.gui.chart.InverseData;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.model.SptModel;
import io.github.liquec.gui.model.SptRow;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static javafx.beans.binding.Bindings.not;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SptController {
    private static final Logger LOG = LoggerFactory.getLogger(SptController.class);

    public TextField textFieldSptDepth;

    public TextField textFieldSptBlowCounts;

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

        // Layer Thickness
        Bindings.bindBidirectional(this.textFieldSptDepth.textProperty(), this.sptModel.sptDepthProperty());
        this.textFieldSptDepth.textProperty().addListener((a, b, c) -> this.manageLayerModelState("SPT Depth", b, c));
        this.textFieldSptDepth.textProperty().addListener((a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldSptDepth,"(\\d{0,2}([\\.]\\d{0,2})?)|100|100\\.|100\\.0|100\\.00", b, c));
        this.textFieldSptDepth.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldSptDepth, b, c, "00", true));

        // Fines Content
        Bindings.bindBidirectional(this.textFieldSptBlowCounts.textProperty(), this.sptModel.sptBlowCountsProperty());
        this.textFieldSptBlowCounts.textProperty().addListener((a, b, c) -> this.manageLayerModelState("SPT Blow Counts", b, c));
        this.textFieldSptBlowCounts.textProperty().addListener(
            (a, b, c) -> this.controllerHelper.validateNumberValue(this.textFieldSptBlowCounts,"(\\d{0,2})|100", b, c));
        this.textFieldSptBlowCounts.focusedProperty().addListener((a, b, c) -> this.controllerHelper.manageZerosValues(this.textFieldSptBlowCounts, b, c, "0", false));

        // Buttons
        buttonOk.setOnAction(e -> saveSpt());
        buttonCancel.setOnAction(e -> exit());

    }

    private void manageLayerModelState(final String name, final String oldValue, final String newValue) {
        this.controllerHelper.trackValues(name, oldValue, newValue);
        this.sptModel.checkAbleToAdd();
    }

    private void saveSpt() {
        this.sessionModel.getSptData().add(this.buildSptRow());
        addSptChartData();
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToAddSpt();
        this.sessionModel.checkAbleToRemoveLastSpt();
        this.exit();
    }

    private SptRow buildSptRow() {
        return new SptRow(
            this.sptModel.getSptDepth(),
            this.sptModel.getSptBlowCounts());
    }

    private void addSptChartData() {
        this.sessionModel.getSptChartDataSeries().getData().add(InverseData.getXyChartInverseData(Integer.valueOf(this.sptModel.getSptBlowCounts()), Float.valueOf(this.sptModel.getSptDepth())));
    }

    private void exit() {
        stage.close();
    }

}
