/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.model;

import io.github.liquec.analysis.model.CalculationModeEnum;
import io.github.liquec.analysis.session.ResultState;
import javafx.beans.property.SimpleStringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ResultModel {
    private static final Logger LOG = LoggerFactory.getLogger(ResultModel.class);

    private final CalculationModeEnum calculationModeEnum;

    public ResultModel(final ResultState state) {
        this.calculationModeEnum = state.getCalculationModeEnum();
    }

    public CalculationModeEnum getCalculationModeEnum() {
        return calculationModeEnum;
    }
}
