/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.session.SessionState;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.nio.file.Path;
import java.util.Optional;

import javax.inject.Singleton;

@Singleton
public class MainModel {
    private final SimpleStringProperty title = new SimpleStringProperty();

    private SessionModel sessionModel;

    private final SimpleBooleanProperty sessionOpen = new SimpleBooleanProperty(false);

    private final SimpleBooleanProperty ableToCalculate = new SimpleBooleanProperty(false);

    private final SimpleBooleanProperty normativeMode = new SimpleBooleanProperty(true);

    private final SimpleObjectProperty<Path> sessionFile = new SimpleObjectProperty<>(null);

    private final SimpleBooleanProperty changesSaved = new SimpleBooleanProperty(true);

    private final SimpleBooleanProperty resultOpen = new SimpleBooleanProperty(false);

    public void replaceSessionModel(final SessionModel sessionModel, final Path sessionFile) {
        unbindOldSession();
        this.sessionModel = sessionModel;
        this.sessionFile.set(sessionFile);
        this.sessionOpen.set(true);
        // session clear
        this.ableToCalculate.bindBidirectional(sessionModel.ableToCalculateProperty());
        this.normativeMode.bindBidirectional(sessionModel.normativeModeProperty());
        this.changesSaved.bindBidirectional(sessionModel.changesSavedProperty());
        this.resultOpen.bindBidirectional(sessionModel.resultOpenProperty());
    }

    private void unbindOldSession() {
        if (this.sessionModel != null) {
            this.ableToCalculate.unbindBidirectional(this.sessionModel.ableToCalculateProperty());
            this.normativeMode.unbindBidirectional(this.sessionModel.normativeModeProperty());
            this.changesSaved.unbindBidirectional(this.sessionModel.changesSavedProperty());
            this.resultOpen.unbindBidirectional(this.sessionModel.resultOpenProperty());
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

    public SimpleBooleanProperty ableToCalculateProperty() {
        return ableToCalculate;
    }

    public SimpleBooleanProperty normativeModeProperty() {
        return normativeMode;
    }

    public SimpleBooleanProperty resultOpenProperty() {
        return resultOpen;
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

    public Optional<SessionModel> getSessionModel() {
        return Optional.ofNullable(sessionModel);
    }
}
