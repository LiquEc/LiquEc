/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import javafx.scene.chart.XYChart;

public class LiquEcData {

    public static Float LOWER_X = -5.0f;
    public static Float UPPER_X = 55.0f;

    public static XYChart.Data<Number, Number> getChartInverseDataLowerX(final Float y) {
        return new XYChart.Data<>(LOWER_X, -y);
    }

    public static XYChart.Data<Number, Number> getChartInverseDataUpperX(final Float y) {
        return new XYChart.Data<>(UPPER_X, -y);
    }

    public static XYChart.Data<Number, Number> getChartInverseDataSpt(final Integer x, final Float y) {
        return new XYChart.Data<>(x, -y);
    }

    public static XYChart.Data<Number, Number> getChartInverseDataSpt(final Double x, final Float y) {
        return new XYChart.Data<>(x, -y);
    }
}
