/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class Runnable {

    protected static final Logger LOG = LoggerFactory.getLogger(Runnable.class);

    protected Mode mode;

    protected String description;

    public Runnable(final Mode mode, final String description) {
        this.mode = mode;
        this.description = description;
    }

    public abstract void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;

    private void logMark() {
        LOG.debug("::: ································································ ");
    }

    protected void logStart() {
        this.logMark();
        LOG.debug("::: START " + this.description + " - Mode " + this.mode.getDescription());
    }

    protected void logEnd() {
        LOG.debug("::: " + this.description + " >>> OK");
        LOG.debug("::: END " + this.description + " - Mode " + this.mode.getDescription());
        this.logMark();
    }
}
