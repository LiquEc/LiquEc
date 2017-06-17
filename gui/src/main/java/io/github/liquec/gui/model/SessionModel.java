/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public final class SessionModel {
    private final SimpleStringProperty projectName;

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    public SessionModel(final String documentName) {
        this.projectName = new SimpleStringProperty(documentName);
    }

    public SimpleStringProperty documentNameProperty() {
        return projectName;
    }

    public SimpleBooleanProperty changesSavedProperty() {
        return changesSaved;
    }

    public void setChangesSaved(final boolean changesSaved) {
        this.changesSaved.set(changesSaved);
    }
}
