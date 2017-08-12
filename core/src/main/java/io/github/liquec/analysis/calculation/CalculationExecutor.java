/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.model.StandardPenetrationTest;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public class CalculationExecutor {

    private static final Logger LOG = LoggerFactory.getLogger(CalculationExecutor.class);

    private CalculationMode calculationMode;
    private SessionState sessionState;
    private ResultState resultState;

    public CalculationExecutor(final CalculationMode calculationMode, final SessionState sessionState) {
        this.calculationMode = calculationMode;
        this.sessionState = sessionState;
        this.resultState = new ResultState();
    }

    public ResultState calculate() {

        this.checkCalculationData();
        this.resultState.setCalculationMode(calculationMode.getMode());
        this.resultState.setGeotechnicalProperties(this.sessionState.getGeotechnicalProperties());

        LOG.debug("Start calculation...");

        for (StandardPenetrationTest standardPenetrationTest : this.sessionState.getStandardPenetrationTestList()) {

            LOG.debug("SPT Blow Counts: " + standardPenetrationTest.getSptBlowCounts() + " (N), Depth: " + standardPenetrationTest.getDepth() + " (m)");

            final SptCalculationResult sptCalculationResult = new SptCalculationResult(standardPenetrationTest);

            for (Enum<? extends Step> step : calculationMode.getSteps()) {
                try {
                    (((Step) step).getStepClass().getConstructor()).newInstance().execute(this.sessionState, sptCalculationResult);

                } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new LiquEcException("LiquEc calculation error");
                }
            }

            this.resultState.getSptCalculationResultList().add(sptCalculationResult);
        }

        LOG.debug("::End calculation");

        return this.resultState;
    }

    private void checkCalculationData() {
        if (this.calculationMode == null) {
            throw new LiquEcException("Calculation mode required");
        }
        if (this.sessionState == null) {
            throw new LiquEcException("Session state required");
        }
        if (this.sessionState.getStandardPenetrationTestList() == null || this.sessionState.getStandardPenetrationTestList().size() == 0) {
            throw new LiquEcException("Standard Penetration Test List required");
        }
    }
}
