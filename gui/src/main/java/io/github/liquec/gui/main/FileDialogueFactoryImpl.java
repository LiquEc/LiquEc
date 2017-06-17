/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import io.github.liquec.gui.dialogues.FileDialogue;
import io.github.liquec.gui.dialogues.FileDialogueFactory;
import io.github.liquec.gui.dialogues.FileDialogueImpl;
import io.github.liquec.gui.dialogues.FileDialogueType;
import io.github.liquec.gui.settings.SettingsManager;
import javafx.stage.Stage;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FileDialogueFactoryImpl implements FileDialogueFactory {
    private final SettingsManager settingsManager;

    @Inject
    public FileDialogueFactoryImpl(final SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
    }

    @Override
    public FileDialogue create(final FileDialogueType type, final Stage stage) {
        return new FileDialogueImpl(type, stage, settingsManager);
    }
}
