/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SptRow {
    private static final Logger LOG = LoggerFactory.getLogger(SptRow.class);

    private final SimpleStringProperty sptDepth;
    private final SimpleStringProperty sptBlowCounts;

    public SptRow(
        final String sptDepth,
        final String sptBlowCounts) {
        this.sptDepth = new SimpleStringProperty(sptDepth);
        this.sptBlowCounts = new SimpleStringProperty(sptBlowCounts);
    }

    public String getSptDepth() {
        return sptDepth.get();
    }

    public SimpleStringProperty sptDepthProperty() {
        return sptDepth;
    }

    public void setSptDepth(final String sptDepth) {
        this.sptDepth.set(sptDepth);
    }

    public String getSptBlowCounts() {
        return sptBlowCounts.get();
    }

    public SimpleStringProperty sptBlowCountsProperty() {
        return sptBlowCounts;
    }

    public void setSptBlowCounts(final String sptBlowCounts) {
        this.sptBlowCounts.set(sptBlowCounts);
    }

}
