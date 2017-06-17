/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.status;

import io.github.liquec.analysis.session.FileNameTool;
import io.github.liquec.gui.model.StatusModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Singleton;

import static javafx.beans.binding.Bindings.*;

@Singleton
public class StatusManagerImpl implements StatusManager {
    private static final Logger LOG = LoggerFactory.getLogger(StatusManagerImpl.class);

    private static final String NAME_NEW_SESSION = "Start a new LiquEc session";

    private static final String NAME_OPEN_SESSION = "Open a saved LiquEc session";

    private static final String DEFAULT_DESCRIPTION = "Liquefaction According to Eurocode";

    private static final String NAME_EXIT = "Exit LiquEc";

    private static final String NAME_ABOUT = "About LiquEc";

    private String name;

    private final SimpleBooleanProperty sessionAvailable = new SimpleBooleanProperty();

    private final SimpleBooleanProperty busy = new SimpleBooleanProperty();

    private final SimpleStringProperty actionDescription = new SimpleStringProperty();

    private final AtomicBoolean gatekeeper = new AtomicBoolean();

    @Inject
    public void setStatusModel(final StatusModel model) {
        model.textProperty().bind(when(busy).then(actionDescription).otherwise(DEFAULT_DESCRIPTION));
        model.busyProperty().bind(busy);
        model.activityProperty().bind(when(busy).then(-1).otherwise(0));
        model.graphShownProperty().bind(and(sessionAvailable, not(busy)));
    }

    @Override
    public boolean beginExit() {
        return begin(NAME_EXIT);
    }

    @Override
    public boolean beginAbout() {
        return begin(NAME_ABOUT);
    }

    private boolean begin(final String name) {
        if (gatekeeper.compareAndSet(false, true)) {
            this.name = name;
            LOG.debug("Begin: {}", name);
            actionDescription.setValue(name);
            busy.setValue(true);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean beginNewSession() {
        return begin(NAME_NEW_SESSION);
    }

    @Override
    public boolean beginOpenSession() {
        return begin(NAME_OPEN_SESSION);
    }

    @Override
    public void markSuccess() {
        LOG.debug("Success: {}", name);
    }

    @Override
    public void completeAction() {
        LOG.debug("Complete: {}", name);
        busy.setValue(false);
        gatekeeper.set(false);
    }

    @Override
    public void performAction() {
        LOG.debug("Perform: {}", name);
    }

    @Override
    public void performAction(final Path file) {
        LOG.debug("Perform: {}", name);
        actionDescription.setValue(String.format("%s: '%s'...", name, FileNameTool.filename(file)));
    }
}
