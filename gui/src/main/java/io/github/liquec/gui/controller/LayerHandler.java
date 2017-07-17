/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.view.ViewFxml;
import io.github.liquec.gui.view.WindowTool;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Provider;

public class LayerHandler {
    @Inject
    private Provider<FXMLLoader> loaderProvider;

    public void show(final String startDepth) {
        Stage stage = new Stage();
        FXMLLoader loader = loaderProvider.get();
        Parent root = ViewFxml.LAYER.loadNode(loader);
        LayerController controller = loader.getController();

        controller.initialise(stage, startDepth);
        WindowTool.setupModal(stage, root, "Geotechnical Layer Properties");
    }
}
