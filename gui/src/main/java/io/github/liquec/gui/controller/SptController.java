/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.gui.chart.LiquEcData;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.model.SptModel;
import io.github.liquec.gui.model.SptRow;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static javafx.beans.binding.Bindings.not;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SptController {
    private static final Logger LOG = LoggerFactory.getLogger(SptController.class);

    public Label valueLastSptDepth;

    public TextField textFieldSptIncreaseDepth;

    public TextField textFieldSptBlowCounts;

    public Button buttonOk;

    public Button buttonCancel;

    private Stage stage;

    private SessionModel sessionModel;

    private SptModel sptModel;

    private String lastSptDepth;

    @Inject
    private ControllerHelper controllerHelper;

    public void initialise(final Stage stage, final SessionModel sessionModel) {
        this.stage = stage;
        this.sessionModel = sessionModel;
        this.initializeLastSptDepth();
        this.sptModel = new SptModel();

        // Start Last SPT Depth
        this.valueLastSptDepth.setText(this.lastSptDepth);

        // Buttons
        buttonOk.disableProperty().bind(not(this.sptModel.ableToAddProperty()));

        // SPT Increase Depth
        Bindings.bindBidirectional(this.textFieldSptIncreaseDepth.textProperty(), this.sptModel.sptIncreaseDepthProperty());
        this.textFieldSptIncreaseDepth.textProperty().addListener((a, b, c) ->
            this.manageLayerModelState("SPT Increase Depth", b, c));
        this.textFieldSptIncreaseDepth.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldSptIncreaseDepth,"((1|2)?\\d{0,1}([\\.]\\d{0,2})?)|30|30\\.|30\\.0|30\\.00", b, c));
        this.textFieldSptIncreaseDepth.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldSptIncreaseDepth, b, c, "00", true));

        // SPT Blow Counts
        Bindings.bindBidirectional(this.textFieldSptBlowCounts.textProperty(), this.sptModel.sptBlowCountsProperty());
        this.textFieldSptBlowCounts.textProperty().addListener((a, b, c) ->
            this.manageLayerModelState("SPT Blow Counts", b, c));
        this.textFieldSptBlowCounts.textProperty().addListener((a, b, c) ->
            this.controllerHelper.validateNumberValue(this.textFieldSptBlowCounts,"((1|2|3|4)?\\d{0,1})|50", b, c));
        this.textFieldSptBlowCounts.focusedProperty().addListener((a, b, c) ->
            this.controllerHelper.manageZerosValues(this.textFieldSptBlowCounts, b, c, "0", false));

        // Buttons
        buttonOk.setOnAction(e -> saveSpt());
        buttonCancel.setOnAction(e -> exit());

    }

    private void initializeLastSptDepth() {
        if (this.sessionModel.getSptData().size() == 0) {
            this.lastSptDepth = "0.00";
            return;
        }
        this.lastSptDepth = this.sessionModel.getSptData().get(this.sessionModel.getSptData().size() - 1).getSptDepth();
    }

    private void manageLayerModelState(final String name, final String oldValue, final String newValue) {
        this.controllerHelper.trackValues(name, oldValue, newValue);
        this.sptModel.checkAbleToAdd();
    }

    private void saveSpt() {
        this.sessionModel.getSptData().add(this.buildSptRow());
        this.addSptChartData();
        this.sessionModel.setChangesSaved(false);
        this.sessionModel.checkAbleToCalculate();
        this.sessionModel.checkAbleToAddSpt();
        this.sessionModel.checkAbleToRemoveLastSpt();
        this.exit();
    }

    private SptRow buildSptRow() {
        return new SptRow(
            this.getFormattedNumber(String.valueOf(this.getFloatValueSptDepth())),
            this.sptModel.getSptBlowCounts());
    }

    private void addSptChartData() {
        this.sessionModel.getSptChartMainDataSeries().getData().add(LiquEcData.getChartInverseDataSptPoint(Integer.valueOf(this.sptModel.getSptBlowCounts()), this.getFloatValueSptDepth()));
        this.sessionModel.drawSptChartPointsLine();
    }

    private Float getFloatValueSptDepth() {
        final Float lastSptDepth = Float.valueOf(this.lastSptDepth);
        final Float sptIncreaseDepth = Float.valueOf(this.sptModel.getSptIncreaseDepth());
        return lastSptDepth + sptIncreaseDepth;
    }

    private String getFormattedNumber(final String number) {
        if (number.matches("(\\d)+[\\.]\\d")) {
            return number + "0";
        }
        return number;
    }

    private void exit() {
        stage.close();
    }

}
