/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.session.SessionState;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.Optional;

@Singleton
public class MainModel {
    private final SimpleStringProperty title = new SimpleStringProperty();

    private SessionState sessionState;

    private SessionModel sessionModel;

    private final SimpleBooleanProperty sessionOpen = new SimpleBooleanProperty(false);

    private final SimpleBooleanProperty sessionCalculate = new SimpleBooleanProperty(false);

    private final SimpleBooleanProperty sessionClear = new SimpleBooleanProperty(false);

    private final SimpleObjectProperty<Path> sessionFile = new SimpleObjectProperty<>(null);

    private final SimpleStringProperty documentName = new SimpleStringProperty();

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    public void replaceSessionModel(final SessionState sessionState, final SessionModel sessionModel, final Path sessionFile) {
        unbindOldSession();

        this.sessionState = sessionState;
        this.sessionModel = sessionModel;
        this.sessionFile.set(sessionFile);
        sessionOpen.set(true);
        // session calculate
        // session clear
        documentName.bind(sessionModel.documentNameProperty());
        changesSaved.bindBidirectional(sessionModel.changesSavedProperty());
    }

    private void unbindOldSession() {
        documentNameProperty().unbind();
        if (sessionState != null) {
            changesSaved.unbindBidirectional(sessionModel.changesSavedProperty());
        }
    }

    public SimpleObjectProperty<Path> sessionFileProperty() {
        return sessionFile;
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(final String title) {
        this.title.set(title);
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

    public boolean isChangesSaved() {
        return changesSaved.get();
    }

    public SimpleBooleanProperty sessionOpenProperty() {
        return sessionOpen;
    }

    public SimpleBooleanProperty sessionCalculateProperty() {
        return sessionCalculate;
    }

    public SimpleBooleanProperty sessionClearProperty() {
        return sessionClear;
    }

    public void setSessionFile(final Path sessionFile) {
        this.sessionFile.set(sessionFile);
    }

    public boolean hasSessionFile() {
        return sessionFile.getValue() != null;
    }

    public Path getSessionFile() {
        return sessionFile.get();
    }

    public Optional<SessionState> getSessionState() {
        return Optional.ofNullable(sessionState);
    }

    public Optional<SessionModel> getSessionModel() {
        return Optional.ofNullable(sessionModel);
    }
}
