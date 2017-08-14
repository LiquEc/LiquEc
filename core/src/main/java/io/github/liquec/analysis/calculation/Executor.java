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

public class Executor {

    private static final Logger LOG = LoggerFactory.getLogger(Executor.class);

    private Mode mode;
    private SessionState sessionState;
    private ResultState resultState;

    public Executor(final Mode mode, final SessionState sessionState) {
        this.mode = mode;
        this.sessionState = sessionState;
        this.resultState = new ResultState();
    }

    public ResultState calculate() {

        this.checkCalculationData();
        this.resultState.setCalculationMode(mode.getDescription());
        this.resultState.setGeotechnicalProperties(this.sessionState.getGeotechnicalProperties());

        LOG.debug("::: START CALCULATION");

        for (StandardPenetrationTest standardPenetrationTest : this.sessionState.getStandardPenetrationTestList()) {

            LOG.debug("::: START - SPT Blow Counts: " + standardPenetrationTest.getSptBlowCounts() + " (N) - Depth: " + standardPenetrationTest.getDepth() + " (m)");

            final SptCalculationResult sptCalculationResult = new SptCalculationResult(standardPenetrationTest);

            for (Enum<? extends Step> step : this.mode.getSteps()) {
                try {

                    (((Step) step).getStepClass().getConstructor(Mode.class)).newInstance(this.mode).execute(this.sessionState, sptCalculationResult);

                } catch (LiquEcException e) {

                    LOG.debug("::: SPT Error: " + e.getMessage());
                    sptCalculationResult.setResult(false);
                    sptCalculationResult.setErrorMessage(e.getMessage());
                    break;

                } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {

                    LOG.debug("::: Error: " + e.getMessage());
                    throw new LiquEcException(e.getMessage());

                }
            }

            this.resultState.getSptCalculationResultList().add(sptCalculationResult);

            LOG.debug("::: END - SPT Blow Counts: " + standardPenetrationTest.getSptBlowCounts() + " (N), Depth: " + standardPenetrationTest.getDepth() + " (m)");
        }

        LOG.debug("::: END CALCULATION");

        return this.resultState;
    }

    private void checkCalculationData() {
        if (this.mode == null) {
            throw new LiquEcException("Calculation mode required");
        }
        if (this.sessionState == null) {
            throw new LiquEcException("Session state required");
        }
        if (this.sessionState.getGeotechnicalProperties().getSoilLayers().size() == 0) {
            throw new LiquEcException("Soil layers list required");
        }
        if (this.sessionState.getStandardPenetrationTestList().size() == 0) {
            throw new LiquEcException("Standard penetration test list required");
        }
    }
}
