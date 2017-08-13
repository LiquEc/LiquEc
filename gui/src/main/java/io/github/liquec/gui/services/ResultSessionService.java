/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import io.github.liquec.analysis.calculation.Executor;
import io.github.liquec.analysis.calculation.Mode;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public class ResultSessionService {

    private static final Logger LOG = LoggerFactory.getLogger(ResultSessionService.class);

    private SessionState sessionState;

    public ResultState calculateSession(final boolean mode, final SessionState sessionState) {
        this.sessionState = sessionState;

        LOG.debug("Calculating...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Executor executor = new Executor(this.getCalculationMode(mode), sessionState);
        final ResultState resultState = executor.calculate();

        LOG.debug("Finish calculation...");

        return resultState;
    }

    private Mode getCalculationMode(final boolean mode) {
        return mode ? Mode.EUROCODE : Mode.NCSE_02;
    }

}
