/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.model.ResultModel;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.services.ControllerHelper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class ResultController {
    private static final Logger LOG = LoggerFactory.getLogger(ResultController.class);

    public Button buttonReturn;

    private SessionModel sessionModel;

    private ResultModel resultModel;

    @Inject
    private GuiResultHandler guiResultHandler;

    @Inject
    private ControllerHelper controllerHelper;

    public void initialise(final SessionModel sessionModel, final ResultModel resultModel) {
        this.sessionModel = sessionModel;
        this.resultModel = resultModel;

        // enable calculation button
        this.sessionModel.setAbleToCalculate(false);

        // Buttons
        buttonReturn.setOnAction(e -> back());

    }

    private void back() {
        // enable calculation button
        this.sessionModel.setAbleToCalculate(true);
        this.guiResultHandler.handleReturn();
    }

}
