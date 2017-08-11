/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CalculationExecutor {

    private CalculationMode calculationMode;
    private SessionState sessionState;
    private ResultState resultState;

    public CalculationExecutor(final CalculationMode calculationMode, final SessionState sessionState) {
        this.calculationMode = calculationMode;
        this.sessionState = sessionState;
        this.resultState = new ResultState();
    }

    public ResultState calculate() {

        if (this.calculationMode == null) {
            throw new LiquEcException("Calculation mode required");
        }

        if (this.sessionState == null) {
            throw new LiquEcException("Session state required");
        }

        this.resultState.setCalculationMode(calculationMode.getMode());

        for (Step step : calculationMode.getSteps()) {
            try {

                Constructor constructor = step.getStepClass().getConstructor();
                Calculation calculation = (Calculation) constructor.newInstance();
                calculation.execute(this.sessionState, this.resultState);

            } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
                throw new LiquEcException("LiquEc calculation error");
            }
        }

        return this.resultState;
    }
}
