/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.model.SoilLayer;
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
        if (this.sessionState.getSiteConditions() == null) {
            throw new LiquEcException("Site conditions required");
        }
        if (this.sessionState.getSiteConditions().getPeakGroundAcceleration() == null) {
            throw new LiquEcException("Peak ground acceleration required");
        }
        if (this.mode.equals(Mode.EUROCODE) && this.sessionState.getSiteConditions().getEarthquakeMagnitude() == null) {
            throw new LiquEcException("Earthquake magnitude required");
        }
        if (this.mode.equals(Mode.NCSE_02) && this.sessionState.getSiteConditions().getCoefficientOfContribution() == null) {
            throw new LiquEcException("Coefficient of contribution required");
        }
        if (this.sessionState.getGeotechnicalProperties().getGroundWaterTableDepth() == null) {
            throw new LiquEcException("Ground water table depth required");
        }
        if (this.sessionState.getGeotechnicalProperties().getSoilLayers().size() == 0) {
            throw new LiquEcException("Soil layers list required");
        }
        for (SoilLayer soilLayer : this.sessionState.getGeotechnicalProperties().getSoilLayers()) {
            if (soilLayer.getStartDepth() == null) {
                throw new LiquEcException("Start depth required");
            }
            if (soilLayer.getFinalDepth() == null) {
                throw new LiquEcException("Final depth required");
            }
            if (soilLayer.getSoilUnitWeight() == null) {
                throw new LiquEcException("Soil unit weight required");
            }
            if (soilLayer.getSoilUnitWeight().getAboveGwt() == null) {
                throw new LiquEcException("Above GWT required");
            }
            if (soilLayer.getSoilUnitWeight().getBelowGwt() == null) {
                throw new LiquEcException("Below GWT required");
            }
            if (soilLayer.getFinesContent() == null) {
                throw new LiquEcException("Fines content required");
            }
        }
        if (this.sessionState.getStandardPenetrationTestList().size() == 0) {
            throw new LiquEcException("Standard penetration test list required");
        }
        for (StandardPenetrationTest standardPenetrationTest : this.sessionState.getStandardPenetrationTestList()) {
            if (standardPenetrationTest.getDepth() == null) {
                throw new LiquEcException("SPT depth required");
            }
            if (standardPenetrationTest.getSptBlowCounts() == null) {
                throw new LiquEcException("SPT blow counts required");
            }
            if (standardPenetrationTest.getEnergyRatio() == null) {
                throw new LiquEcException("Energy ratio required");
            }
        }
    }
}
