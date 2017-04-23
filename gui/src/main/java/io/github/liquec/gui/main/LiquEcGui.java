/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import io.github.liquec.gui.controller.MainController;
import io.github.liquec.gui.common.Placement;
import io.github.liquec.gui.services.PlacementManager;
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

    public void start(final Stage stage) {
        Parent root = ViewFxml.MAIN.loadNode(mainLoader);

        initialise(stage);

        Scene scene = new Scene(root);

        scene.setOnKeyPressed(this::handleKeyEvent);
        stage.setOnCloseRequest(mainController.getCloseRequestHandler());

        Placement placement = placementManager.getMainWindow();

        stage.setScene(scene);
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
    }

    private void handleKeyEvent(final KeyEvent event) {

    }
}
