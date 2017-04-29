/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.gui.model.MainModel;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GuiFileHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GuiFileHandler.class);

    private Stage stage;

    @Inject
    private MainModel model;

    public void initialise(final Stage stage) {
        this.stage = stage;
    }

    public boolean unsavedChangesCheck() {
        return true;
//        if (model.isChangesSaved()) {
//            return true;
//        } else {
//            UnsavedChangesDialogue dialogue = new UnsavedChangesDialogue(model.getSessionFile());
//
//            dialogue.showDialogue();
//
//            switch (dialogue.getUserResponse()) {
//                case SAVE:
//                    return saveChanges();
//                case DISCARD:
//                    return true;
//                default:
//                    return false;
//            }
//        }
    }
}
