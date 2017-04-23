/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.StatusBar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class MainController {
    public MenuItem menuNew;

    public MenuItem menuOpen;

    public MenuItem menuSave;

    public MenuItem menuSaveAs;

    public RadioMenuItem menuEditOn;

    public RadioMenuItem menuEditOff;

    public MenuItem menuExport;

    public MenuItem menuSetupFilters;

    public CheckMenuItem menuEnableFilters;

    public MenuItem menuWebsite;

    public MenuItem menuHowTo;

    public MenuItem menuIssue;

    public MenuItem menuAbout;

    public Button buttonNew;

    public Button buttonOpen;

    public Button buttonSave;

    public Button buttonExport;

    public Button buttonSetupFilters;

    public CheckBox buttonEnableFilters;

    public RadioButton buttonEditOn;

    public RadioButton buttonEditOff;

    public BorderPane mainBorderPane;

    public MenuBar menuBar;

    public StatusBar statusBar;

    public Pane maskerPane;

    public MenuItem menuFind;

    public MenuItem menuExit;

    public void initialise(final Stage stage) {

    }

    public EventHandler<WindowEvent> getCloseRequestHandler() {
        return this::processCloseRequest;
    }

    private void processCloseRequest(final WindowEvent e) {

    }
}
