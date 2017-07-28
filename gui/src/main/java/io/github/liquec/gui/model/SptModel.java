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

    private final SimpleStringProperty depth;
    private final SimpleStringProperty sptBlowCounts;
    private final SimpleStringProperty energyRatio;

    public SptModel() {
        this.depth = new SimpleStringProperty();
        this.sptBlowCounts = new SimpleStringProperty();
        this.energyRatio = new SimpleStringProperty();
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

    public String getEnergyRatio() {
        return energyRatio.get();
    }

    public SimpleStringProperty energyRatioProperty() {
        return energyRatio;
    }

    public void setEnergyRatio(final String energyRatio) {
        this.energyRatio.set(energyRatio);
    }

    public void checkAbleToAdd() {
        LOG.debug("Checking able to add a SPT...");
        boolean ableToAdd = true;
        try {
            LOG.debug("depth: " + this.getDepth());
            if (StringUtils.isEmpty(this.getDepth())) {
                throw new LiquEcException("depth");
            }
            LOG.debug("sptBlowCounts: " + this.getSptBlowCounts());
            if (StringUtils.isEmpty(this.getSptBlowCounts())) {
                throw new LiquEcException("sptBlowCounts");
            }
            LOG.debug("energyRatio: " + this.getEnergyRatio());
            if (StringUtils.isEmpty(this.getEnergyRatio())) {
                throw new LiquEcException("depth");
            }
        } catch (LiquEcException e) {
            LOG.debug("Required value: " + e.getMessage());
            ableToAdd = false;
        }
        this.setAbleToAdd(ableToAdd);
        LOG.debug("isAbleToAdd: " + this.isAbleToAdd());
    }
}
