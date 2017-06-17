/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.gui.model.SessionModel;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    public Label labelProjectName;

    public TextField textFieldProjectName;

    private SessionModel sessionModel;

    public void initialise(final GuiTaskHandler guiTaskHandler, final SessionModel sessionModel) {
        this.sessionModel = sessionModel;
        this.textFieldProjectName.textProperty().bind(this.sessionModel.documentNameProperty());

    }
}
