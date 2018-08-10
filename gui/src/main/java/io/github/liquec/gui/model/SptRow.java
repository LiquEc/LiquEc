/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SptRow implements Comparable<SptRow> {
    private static final Logger LOG = LoggerFactory.getLogger(SptRow.class);

    private final SimpleStringProperty sptDepth;
    private final SimpleStringProperty sptBlowCounts;
    private final SimpleStringProperty sptEnergyRatio;

    public SptRow(
        final String sptDepth,
        final String sptBlowCounts,
        final String sptEnergyRatio) {
        this.sptDepth = new SimpleStringProperty(sptDepth);
        this.sptBlowCounts = new SimpleStringProperty(sptBlowCounts);
        this.sptEnergyRatio = new SimpleStringProperty(sptEnergyRatio);
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

    public String getSptEnergyRatio() {
        return sptEnergyRatio.get();
    }

    public SimpleStringProperty sptEnergyRatioProperty() {
        return sptEnergyRatio;
    }

    public void setSptEnergyRatio(final String sptEnergyRatio) {
        this.sptEnergyRatio.set(sptEnergyRatio);
    }

    public int compareTo(final SptRow sptRow) {
        if (Float.valueOf(this.sptDepth.getValue()).equals(Float.valueOf(sptRow.getSptDepth()))) {
            return 0;
        }
        return Float.valueOf(this.sptDepth.getValue()) < Float.valueOf(sptRow.getSptDepth()) ? -1 : 1;
    }
}
