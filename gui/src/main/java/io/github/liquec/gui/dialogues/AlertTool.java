/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.dialogues;

import javafx.scene.control.Alert;

public final class AlertTool {

    private AlertTool() {
        // Prevent instantiation - all methods are static
    }

    public static void filterErrorAlert(final String title, final String headerText, final String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId("filterErrorDialogue");

        alert.showAndWait();
    }
}
