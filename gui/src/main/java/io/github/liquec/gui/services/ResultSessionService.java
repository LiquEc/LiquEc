/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import io.github.liquec.analysis.model.CalculationModeEnum;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import javax.inject.Singleton;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.l;

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
        LOG.debug("Finish calculation...");

        return new ResultState(this.getCalculationMode(mode));
    }

    private CalculationModeEnum getCalculationMode(final boolean mode) {
        return mode ? CalculationModeEnum.EUROCODE : CalculationModeEnum.NCSE02;
    }

}
