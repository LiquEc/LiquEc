/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.settings.SettingsManager;
import io.github.liquec.gui.settings.WindowSettings;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ExitRequestHandler {
    @Inject
    private GuiFileHandler guiFileHandler;

    @Inject
    private SettingsManager settingsManager;

    private Stage stage;

    public void initialise(final Stage stage) {
        this.stage = stage;
    }

    public boolean handleExitRequest(final WindowEvent e) {
        boolean isContinue = guiFileHandler.unsavedChangesCheck();

        if (isContinue) {
            WindowSettings windowSettings = new WindowSettings();

            windowSettings.setX(stage.getX());
            windowSettings.setY(stage.getY());
            windowSettings.setWidth(stage.getWidth());
            windowSettings.setHeight(stage.getHeight());

            settingsManager.setWindowSettings(windowSettings);
        } else {
            e.consume();
        }

        return isContinue;
    }
}
