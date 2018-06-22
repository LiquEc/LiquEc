/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.common.ControllerAndView;
import io.github.liquec.gui.view.ViewFxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

@Singleton
public class ResultProvider implements Provider<ControllerAndView<ResultController, Node>> {
    @Inject
    private Provider<FXMLLoader> loaderProvider;

    @Override
    public ControllerAndView<ResultController, Node> get() {
        FXMLLoader loader = loaderProvider.get();
        Node root = ViewFxml.RESULT.loadNode(loader);
        ResultController controller = loader.getController();

        return new ControllerAndView<>(controller, root);
    }
}
