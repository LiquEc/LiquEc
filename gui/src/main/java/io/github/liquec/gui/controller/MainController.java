/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.gui.common.GuiConstants;
import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.model.StatusModel;
import io.github.liquec.gui.services.WebPageTool;
import io.github.liquec.gui.status.StatusManager;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.StatusBar;

import javax.inject.Inject;
import javax.inject.Singleton;

import static javafx.beans.binding.Bindings.not;

@Singleton
@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class MainController {
    public MenuItem menuNew;

    public MenuItem menuOpen;

    public MenuItem menuSave;

    public MenuItem menuSaveAs;

    public MenuItem menuEurocode;

    public MenuItem menuNCSE02;

    public MenuItem menuClear;

    public MenuItem menuWebsite;

    public MenuItem menuHowTo;

    public MenuItem menuIssue;

    public MenuItem menuAbout;

    public Button buttonSave;

    public Button buttonNew;

    public Button buttonOpen;

    public Button buttonEurocode;

    public Button buttonNCSE02;

    public BorderPane mainBorderPane;

    public MenuBar menuBar;

    public StatusBar statusBar;

    public Pane maskerPane;

    @Inject
    private MainModel model;

    @Inject
    private StatusModel statusModel;

    @Inject
    private StatusManager statusManager;

    @Inject
    private ExitRequestHandler exitRequestHandler;

    @Inject
    private AboutHandler aboutHandler;

    @Inject
    private WebPageTool webPageTool;

    @Inject
    private SessionStateHandler sessionStateHandler;

    public void initialise(final Stage stage) {
        sessionStateHandler.initialise(mainBorderPane);

        buttonSave.disableProperty().bind(not(model.sessionOpenProperty()));
        menuSave.disableProperty().bind(not(model.sessionOpenProperty()));
        menuSaveAs.disableProperty().bind(not(model.sessionOpenProperty()));

        buttonEurocode.disableProperty().bind(not(model.sessionCalculateProperty()));
        buttonNCSE02.disableProperty().bind(not(model.sessionCalculateProperty()));
        menuEurocode.disableProperty().bind(not(model.sessionCalculateProperty()));
        menuNCSE02.disableProperty().bind(not(model.sessionCalculateProperty()));

        menuClear.disableProperty().bind(not(model.sessionClearProperty()));

        menuWebsite.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBSITE));
        menuHowTo.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBPAGE_HELP));
        menuIssue.setOnAction(e -> webPageTool.showWebPage(GuiConstants.WEBPAGE_ISSUE));
        menuAbout.setOnAction(e -> processAbout());

        prepareStatusInformation();

    }

    private void prepareStatusInformation() {
        statusBar.textProperty().bind(statusModel.textProperty());
        statusBar.progressProperty().bind(statusModel.activityProperty());
        statusBar.getRightItems().add(MiniGraphTool.miniGraph(statusModel));

        maskerPane.visibleProperty().bind(statusModel.busyProperty());
    }

    public void processAbout() {
        if (statusManager.beginAbout()) {
            try {
                aboutHandler.show();
                statusManager.markSuccess();
            } finally {
                statusManager.completeAction();
            }
        }
    }

    public EventHandler<WindowEvent> getCloseRequestHandler() {
        return this::processCloseRequest;
    }

    private void processCloseRequest(final WindowEvent e) {
        if (statusManager.beginExit()) {
            try {
                if (exitRequestHandler.handleExitRequest(e)) {
                    statusManager.markSuccess();
                }
            } finally {
                statusManager.completeAction();
            }
        }
    }
}
