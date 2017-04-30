/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.status;

import io.github.liquec.gui.model.StatusModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import javax.inject.Inject;
import javax.inject.Singleton;

import static javafx.beans.binding.Bindings.when;

@Singleton
public class StatusManagerImpl implements StatusManager {
    private static final Logger LOG = LoggerFactory.getLogger(StatusManagerImpl.class);

    private static final String NAME_EXIT = "Exit LiquEc";

    private String name;

    private final SimpleBooleanProperty busy = new SimpleBooleanProperty();

    private final SimpleStringProperty actionDescription = new SimpleStringProperty();

    private final AtomicBoolean gatekeeper = new AtomicBoolean();

    @Inject
    public void setStatusModel(final StatusModel model) {
        model.busyProperty().bind(busy);
        model.activityProperty().bind(when(busy).then(-1).otherwise(0));
    }

    @Override
    public boolean beginExit() {
        return begin(NAME_EXIT);
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
    public void markSuccess() {
        LOG.debug("Success: {}", name);
    }

    @Override
    public void completeAction() {
        LOG.debug("Complete: {}", name);
        busy.setValue(false);
        gatekeeper.set(false);
    }
}
