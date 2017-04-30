/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.inject.Singleton;

@Singleton
public class StatusModel {
    private final SimpleDoubleProperty activity = new SimpleDoubleProperty();

    private final SimpleBooleanProperty busy = new SimpleBooleanProperty();

    public SimpleDoubleProperty activityProperty() {
        return activity;
    }

    public SimpleBooleanProperty busyProperty() {
        return busy;
    }
}
