/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.core.LiquEcException;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SptModel {
    private static final Logger LOG = LoggerFactory.getLogger(SptModel.class);

    private final SimpleBooleanProperty ableToAdd = new SimpleBooleanProperty(false);

    private final SimpleStringProperty sptIncreaseDepth;
    private final SimpleStringProperty sptBlowCounts;

    public SptModel() {
        this.sptIncreaseDepth = new SimpleStringProperty();
        this.sptBlowCounts = new SimpleStringProperty();
        this.checkAbleToAdd();
    }

    public boolean isAbleToAdd() {
        return ableToAdd.get();
    }

    public SimpleBooleanProperty ableToAddProperty() {
        return ableToAdd;
    }

    public void setAbleToAdd(final boolean ableToAdd) {
        this.ableToAdd.set(ableToAdd);
    }

    public String getSptIncreaseDepth() {
        return sptIncreaseDepth.get();
    }

    public SimpleStringProperty sptIncreaseDepthProperty() {
        return sptIncreaseDepth;
    }

    public void setSptIncreaseDepth(final String sptIncreaseDepth) {
        this.sptIncreaseDepth.set(sptIncreaseDepth);
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

    public void checkAbleToAdd() {
        LOG.debug("Checking able to add a SPT...");
        boolean ableToAdd = true;
        try {
            LOG.debug("sptIncreaseDepth: " + this.getSptIncreaseDepth());
            if (StringUtils.isEmpty(this.getSptIncreaseDepth())) {
                throw new LiquEcException("sptIncreaseDepth");
            }
            LOG.debug("sptBlowCounts: " + this.getSptBlowCounts());
            if (StringUtils.isEmpty(this.getSptBlowCounts())) {
                throw new LiquEcException("sptBlowCounts");
            }
        } catch (LiquEcException e) {
            LOG.debug("Required value: " + e.getMessage());
            ableToAdd = false;
        }
        this.setAbleToAdd(ableToAdd);
        LOG.debug("isAbleToAdd: " + this.isAbleToAdd());
    }
}
