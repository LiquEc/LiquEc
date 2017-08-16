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

    public Runnable(final Mode mode) {
        this.mode = mode;
    }

    public abstract void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
