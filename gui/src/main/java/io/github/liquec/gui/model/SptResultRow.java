/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SptResultRow {
    private static final Logger LOG = LoggerFactory.getLogger(SptResultRow.class);

    private final SimpleStringProperty mode;
    private final SimpleBooleanProperty result;

    private final SimpleStringProperty depth;
    private final SimpleStringProperty sptBlowCounts;
    private final SimpleStringProperty sptCorrected;
    private final SimpleStringProperty csr;
    private final SimpleStringProperty crr;
    private final SimpleStringProperty safetyFactor;

    public SptResultRow(
        final String mode,
        final Boolean result,
        final String depth,
        final String sptBlowCounts,
        final String sptCorrected,
        final String csr,
        final String crr,
        final String safetyFactor) {
        this.mode = new SimpleStringProperty(mode);
        this.result = new SimpleBooleanProperty(result);
        this.depth = new SimpleStringProperty(depth);
        this.sptBlowCounts = new SimpleStringProperty(sptBlowCounts);
        this.sptCorrected = new SimpleStringProperty(sptCorrected);
        this.csr = new SimpleStringProperty(csr);
        this.crr = new SimpleStringProperty(crr);
        this.safetyFactor = new SimpleStringProperty(safetyFactor);
    }

    public String getMode() {
        return mode.get();
    }

    public SimpleStringProperty modeProperty() {
        return mode;
    }

    public boolean isResult() {
        return result.get();
    }

    public SimpleBooleanProperty resultProperty() {
        return result;
    }

    public void setResult(final boolean result) {
        this.result.set(result);
    }

    public String getDepth() {
        return depth.get();
    }

    public SimpleStringProperty depthProperty() {
        return depth;
    }

    public void setDepth(final String depth) {
        this.depth.set(depth);
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

    public String getSptCorrected() {
        return sptCorrected.get();
    }

    public SimpleStringProperty sptCorrectedProperty() {
        return sptCorrected;
    }

    public void setSptCorrected(final String sptCorrected) {
        this.sptCorrected.set(sptCorrected);
    }

    public String getCsr() {
        return csr.get();
    }

    public SimpleStringProperty csrProperty() {
        return csr;
    }

    public void setCsr(final String csr) {
        this.csr.set(csr);
    }

    public String getCrr() {
        return crr.get();
    }

    public SimpleStringProperty crrProperty() {
        return crr;
    }

    public void setCrr(final String crr) {
        this.crr.set(crr);
    }

    public String getSafetyFactor() {
        return safetyFactor.get();
    }

    public SimpleStringProperty safetyFactorProperty() {
        return safetyFactor;
    }

    public void setSafetyFactor(final String safetyFactor) {
        this.safetyFactor.set(safetyFactor);
    }
}
