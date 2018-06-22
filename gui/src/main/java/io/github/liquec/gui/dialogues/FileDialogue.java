/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.dialogues;

import java.nio.file.Path;

public interface FileDialogue {
    void showChooser();

    boolean isFileSelected();

    Path getSelectedFile();

    FileFormatType getFileFormatType();
}
