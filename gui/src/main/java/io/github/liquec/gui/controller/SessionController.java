/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.gui.model.SessionModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static io.github.liquec.gui.common.FieldValueTool.*;

@SuppressFBWarnings({"NP_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD", "UWF_UNWRITTEN_PUBLIC_OR_PROTECTED_FIELD"})
public class SessionController {
    private static final Logger LOG = LoggerFactory.getLogger(SessionController.class);

    public TextField textFieldProjectName;

    private SessionModel sessionModel;

    public void initialise(final GuiTaskHandler guiTaskHandler, final SessionModel sessionModel) {
        this.sessionModel = sessionModel;

        this.textFieldProjectName.setPromptText("Enter project name...");

        Bindings.bindBidirectional(textFieldProjectName.textProperty(), this.sessionModel.projectNameProperty());
        manageChangesSaved(this.sessionModel.projectNameProperty());

    }

    private void manageChangesSaved(final Property<String> property) {
        property.addListener((arg0, arg1, arg2) -> {
            LOG.debug(property.getName() + " old value: " + arg1);
            LOG.debug(property.getName() + " new value: " + arg2);
            sessionModel.setChangesSaved(false);
        });
    }


}
