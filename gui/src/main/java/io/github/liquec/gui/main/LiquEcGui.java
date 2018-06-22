/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import io.github.liquec.gui.common.Placement;
import io.github.liquec.gui.controller.ExitRequestHandler;
import io.github.liquec.gui.controller.GuiFileHandler;
import io.github.liquec.gui.controller.MainController;
import io.github.liquec.gui.controller.TitleHandler;
import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.services.PlacementManager;
import io.github.liquec.gui.settings.LiquEcDimensions;
import io.github.liquec.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LiquEcGui {
    @Inject
    private FXMLLoader mainLoader;

    @Inject
    private MainController mainController;

    @Inject
    private PlacementManager placementManager;

    @Inject
    private GuiFileHandler guiFileHandler;

    @Inject
    private ExitRequestHandler exitRequestHandler;

    @Inject
    private TitleHandler titleHandler;

    @Inject
    private MainModel model;

    public void start(final Stage stage) {
        Parent root = ViewFxml.MAIN.loadNode(mainLoader);

        initialise(stage);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(this::handleKeyEvent);
        stage.setOnCloseRequest(mainController.getCloseRequestHandler());

        Placement placement = placementManager.getMainWindow();

        stage.setScene(scene);
        stage.setMinWidth(LiquEcDimensions.MIN_WIDTH.getDimension());
        stage.setMinHeight(LiquEcDimensions.MIN_HEIGHT.getDimension());
//        stage.setMaxWidth(LiquEcDimensions.MAX_WIDTH.getDimension());
//        stage.setMaxHeight(LiquEcDimensions.MAX_HEIGHT.getDimension());
        stage.setWidth(placement.getWidth());
        stage.setHeight(placement.getHeight());
        if (placement.isPositioned()) {
            stage.setX(placement.getX());
            stage.setY(placement.getY());
        }
        stage.show();
    }

    private void initialise(final Stage stage) {
        mainController.initialise(stage);
        guiFileHandler.initialise(stage);
        exitRequestHandler.initialise(stage);
        titleHandler.initialise();

        stage.titleProperty().bind(model.titleProperty());
    }

    private void handleKeyEvent(final KeyEvent event) {

    }
}
