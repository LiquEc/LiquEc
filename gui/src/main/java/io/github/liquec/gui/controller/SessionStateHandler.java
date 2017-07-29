/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;
import io.github.liquec.gui.common.ControllerAndView;
import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.model.ResultModel;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.settings.SettingsManager;
import io.github.liquec.gui.settings.WindowSettings;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionStateHandler {

    @Inject
    private SettingsManager settingsManager;

    @Inject
    private MainModel model;

    @Inject
    private SessionProvider sessionProvider;

    @Inject
    private ResultProvider resultProvider;

    @Inject
    private GuiTaskHandler guiTaskHandler;

    private BorderPane mainBorderPane;

    public void initialise(final BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public SessionState getSessionState() {
        return model.getSessionModel().orElseThrow(() -> new LiquEcException("No session model available")).getSessionState();
    }

    public SessionModel addSession(final SessionState state) {
        SessionModel sessionModel = new SessionModel(state);
        ControllerAndView<SessionController, Node> cav = sessionProvider.get();
        SessionController controller = cav.getController();
        controller.initialise(sessionModel);
        mainBorderPane.setCenter(cav.getView());
        return sessionModel;
    }

    public void clearSessionModel() {
        model.getSessionModel().orElseThrow(() -> new LiquEcException("No session model available")).clearSessionModelData();
    }

    public void showResultView(final ResultState state) {
        ResultModel resultModel = new ResultModel(state);
        ControllerAndView<ResultController, Node> cav = resultProvider.get();
        ResultController controller = cav.getController();
        controller.initialise(model.getSessionModel().get(), resultModel);
        mainBorderPane.setCenter(cav.getView());
    }

    public void returnToSessionView() {
        ControllerAndView<SessionController, Node> cav = sessionProvider.get();
        SessionController controller = cav.getController();
        controller.initialise(model.getSessionModel().get());
        mainBorderPane.setCenter(cav.getView());
    }
}
