/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.services.ResultSessionService;
import io.github.liquec.gui.status.GuiTask;
import io.github.liquec.gui.status.StatusManager;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GuiResultHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GuiResultHandler.class);

    private Stage stage;

    @Inject
    private ResultSessionService resultSessionService;

    @Inject
    private StatusManager statusManager;

    @Inject
    private MainModel model;

    @Inject
    private SessionStateHandler sessionStateHandler;

    @Inject
    private GuiTaskHandler guiTaskHandler;

    public void initialise(final Stage stage) {
        this.stage = stage;
    }

    public void handleCalculation() {
        if (statusManager.beginCalculation()) {
            this.processCalculation();
        }
    }

    private void processCalculation() {
        statusManager.performAction();
        LOG.info("Process calculation '{}'");

        GuiTask<ResultState> task = new GuiTask<>(
            guiTaskHandler,
            statusManager,
            () -> resultSessionService.calculateSession(),
            this::finishCalculation,
            e -> new LiquEcException("Error calculating LiquEc session"));

        guiTaskHandler.executeInBackground(task);
    }

    private void finishCalculation(final ResultState resultState) {
        LOG.info("Finish calculation '{}'");
        sessionStateHandler.showResultView(resultState);
    }

    public void handleReturn() {
        sessionStateHandler.returnToSessionView();
    }

}
