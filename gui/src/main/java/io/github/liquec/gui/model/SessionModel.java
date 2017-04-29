/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public final class SessionModel {
    private final SimpleStringProperty documentName;

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    public SessionModel(final String documentName) {
        this.documentName = new SimpleStringProperty(documentName);
    }

    public SimpleStringProperty documentNameProperty() {
        return documentName;
    }

    public SimpleBooleanProperty changesSavedProperty() {
        return changesSaved;
    }

    public void setChangesSaved(final boolean changesSaved) {
        this.changesSaved.set(changesSaved);
    }
}
