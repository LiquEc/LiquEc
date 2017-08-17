/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import io.github.liquec.gui.common.BoundsEnum;
import io.github.liquec.gui.model.SessionModel;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class ChartHelperImpl implements ChartHelper {

    public Double lowerBoundAxisY(final SessionModel sessionModel) {
        return this.getPairValueAxisY((sessionModel.getLayerData().size() > 0)
            ? -Math.ceil(Double.valueOf(sessionModel.getLayerData().get(sessionModel.getLayerData().size() - 1).getFinalDepth()) + 1) : BoundsEnum.MAX_DEPTH.getNegativeValue());
    }

    public Double getPairValueAxisX(final Double value) {
        return (value % 2 == 0) ? value : value + 1;
    }

    public Double getPairValueAxisY(final Double value) {
        return (value % 2 == 0) ? value : value - 1;
    }

    public Double tickUnitAxisX(final Double upperBoundAxisX) {
        if (upperBoundAxisX <= 5) {
            return 1.0;
        }
        if (upperBoundAxisX <= 10) {
            return 2.0;
        }
        if (upperBoundAxisX <= 30) {
            return 5.0;
        }
        return 10.0;
    }

    public Double tickUnitAxisY(final Double lowerBoundAxisY) {
        if (lowerBoundAxisY >= -10) {
            return 1.0;
        }
        if (lowerBoundAxisY >= -20) {
            return 2.0;
        }
        return 5.0;
    }

    public Double ceil(final double value, final int precision) {
        BigDecimal a = new BigDecimal(value);
        BigDecimal b = a.setScale(precision, RoundingMode.CEILING);
        return b.doubleValue();
    }

}
