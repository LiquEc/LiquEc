/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.dialogues;

import javafx.stage.Stage;

public interface FileDialogueFactory {
    FileDialogue create(final FileDialogueType type, final Stage stage);
}
