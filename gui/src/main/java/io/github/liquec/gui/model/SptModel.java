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

    private final SimpleStringProperty sptDepth;
    private final SimpleStringProperty sptBlowCounts;

    public SptModel() {
        this.sptDepth = new SimpleStringProperty();
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

    public void checkAbleToAdd() {
        LOG.debug("Checking able to add a SPT...");
        boolean ableToAdd = true;
        try {
            LOG.debug("sptDepth: " + this.getSptDepth());
            if (StringUtils.isEmpty(this.getSptDepth())) {
                throw new LiquEcException("sptDepth");
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
