/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.controller;

import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.EnrichedSessionState;
import io.github.liquec.analysis.session.FileNameTool;
import io.github.liquec.analysis.session.SessionState;
import io.github.liquec.gui.dialogues.*;
import io.github.liquec.gui.model.MainModel;
import io.github.liquec.gui.model.SessionModel;
import io.github.liquec.gui.services.SessionFileService;
import io.github.liquec.gui.status.GuiTask;
import io.github.liquec.gui.status.StatusManager;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GuiFileHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GuiFileHandler.class);

    private Stage stage;

    @Inject
    private FileDialogueFactory fileDialogueFactory;

    @Inject
    private SessionFileService sessionFileService;

    @Inject
    private StatusManager statusManager;

    @Inject
    private MainModel model;

    @Inject
    private SessionStateHandler sessionStateHandler;

    @Inject
    private GuiTaskHandler guiTaskHandler;

    public void initialise(final Stage stage) {
        this.stage = stage;
    }

    public boolean unsavedChangesCheck() {
        if (model.isChangesSaved()) {
            return true;
        } else {
            UnsavedChangesDialogue dialogue = new UnsavedChangesDialogue(model.getSessionFile());

            dialogue.showDialogue();

            switch (dialogue.getUserResponse()) {
                case SAVE:
                    return saveChanges();
                case DISCARD:
                    return true;
                default:
                    return false;
            }
        }
    }

    public void handleNewSession() {
        if (statusManager.beginNewSession()) {
            this.processNewSession();
        }
    }

    private void processNewSession() {
        if (unsavedChangesCheck()) {
            statusManager.performAction();
            LOG.info("New session from '{}'");

            GuiTask<EnrichedSessionState> task = new GuiTask<>(
                guiTaskHandler,
                statusManager,
                () -> sessionFileService.createNewSession(),
                this::finishOpen,
                e -> new LiquEcException("Error creating new LiquEc session"));

            guiTaskHandler.executeInBackground(task);
            LOG.info("New LiquEc session started");
        } else {
            statusManager.completeAction();
        }
    }

    public void handleOpenSession() {
        if (statusManager.beginOpenSession()) {
            guiTaskHandler.pauseThenExecuteOnGuiThread(this::processOpenSession);
        }
    }

    private void processOpenSession() {
        if (unsavedChangesCheck()) {
            Path file = chooseFile(FileDialogueType.OPEN_SESSION);
            statusManager.performAction(file);
            LOG.info("New session from '{}'", file);

            GuiTask<EnrichedSessionState> task = new GuiTask<>(
                guiTaskHandler,
                statusManager,
                () -> sessionFileService.createNewSession(file),
                this::finishOpen,
                e -> FileErrorTool.open(file, e));

            guiTaskHandler.executeInBackground(task);
            LOG.info("LiquEc session opened");
        } else {
            statusManager.completeAction();
        }
    }

    private Path chooseFile(final FileDialogueType type) {
        FileDialogue dialogue = fileDialogueFactory.create(type, stage);

        dialogue.showChooser();
        if (dialogue.isFileSelected()) {
            return dialogue.getSelectedFile();
        } else {
            return null;
        }
    }

    private void finishOpen(final EnrichedSessionState enrichedState) {
        SessionState state = enrichedState.getState();
        SessionModel sessionModel = sessionStateHandler.addSession(state);

        model.replaceSessionModel(state, sessionModel, enrichedState.getFile().orElse(null));
    }

    private boolean saveChanges() {
//        if (model.hasSessionFile()) {
//            saveChangesInternal();
//
//            return true;
//        } else {
//            return saveChangesAs();
//        }
        return true;
    }

//    private boolean saveChangesAs() {
//        Path file = chooseFile(FileDialogueType.SAVE_SESSION);
//
//        if (file == null) {
//            return false;
//        } else {
//            file = FileNameTool.ensureSessionFileHasSuffix(file);
//
//            model.setSessionFile(file);
//
//            return saveChangesInternal();
//        }
//    }
//
//    private boolean saveChangesInternal() {
//        Path file = model.getSessionFile();
//
//        try {
//            LOG.info("Saving file '{}'", file);
//            sessionFileService.write(file, sessionStateHandler.getSessionState());
//            model.setChangesSaved(true);
//
//            return true;
//        } catch (final RuntimeException e) {
//            FileErrorTool.save(file, e);
//
//            return false;
//        }
//    }
//
//    private Path chooseFile(final FileDialogueType type) {
//        FileDialogue dialogue = fileDialogueFactory.create(type, stage);
//
//        dialogue.showChooser();
//        if (dialogue.isFileSelected()) {
//            return dialogue.getSelectedFile();
//        } else {
//            return null;
//        }
//    }

}
