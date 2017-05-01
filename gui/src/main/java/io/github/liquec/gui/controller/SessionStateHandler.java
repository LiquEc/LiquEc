/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.SessionState;
import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.settings.SettingsManager;
import javafx.scene.layout.BorderPane;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionStateHandler {

    @Inject
    private SettingsManager settingsManager;

    @Inject
    private MainModel model;

//    @Inject
//    private SessionProvider sessionProvider;
//
//    @Inject
//    private ProgressProvider progressProvider;
//
//    @Inject
//    private GuiTaskHandler guiTaskHandler;

    private BorderPane mainBorderPane;

//    private SessionActions sessionActions;

    public void initialise(final BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    public SessionState getSessionState() {
        return model.getSessionState().orElseThrow(() -> new LiquEcException("No session state available"));
    }

//    public SessionModel addSession(final SessionState state) {
//        SessionViewTool viewTool = new SessionViewTool();
//        SessionModelTool sessionTool = new SessionModelTool(state, model.getFilterSettings(), viewTool.selectedProperty(), settingsManager.getWindowSettings().orElseGet(WindowSettings::new));
//        SessionModel sessionModel = sessionTool.buildModel();
//        ControllerAndView<SessionController, Node> cav = sessionProvider.get();
//        SessionController controller = cav.getController();
//
//        controller.initialise(guiTaskHandler, sessionModel);
//        viewTool.setTabContent(SessionTab.ANALYSIS, cav.getView());
//        viewTool.setTabContent(SessionTab.PROGRESS, progressView(sessionModel));
//        mainBorderPane.setCenter(viewTool.getView());
//
//        sessionActions = controller.getSessionActions();
//
//        return sessionModel;
//    }
//
//    private Node progressView(final SessionModel sessionModel) {
//        ControllerAndView<ProgressController, Node> cav = progressProvider.get();
//
//        cav.getController().initialise(sessionModel.getProgress());
//
//        return cav.getView();
//    }
//
//    public Optional<SessionActions> getSessionActions() {
//        return Optional.ofNullable(sessionActions);
//    }
}
