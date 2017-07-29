/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import io.github.liquec.analysis.session.ResultState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

@Singleton
public class ResultSessionService {

    private static final Logger LOG = LoggerFactory.getLogger(ResultSessionService.class);

    public ResultState calculateSession() {
        LOG.debug("Calculating...");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOG.debug("Finish calculation...");
        return new ResultState();
    }

}
