/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import javafx.scene.chart.XYChart;

public class InverseData {

    public static Float LOWER_X = -5.0f;
    public static Float UPPER_X = 105.0f;


    public static XYChart.Data getXyChartInverseDataLower(final Float y) {
        return new XYChart.Data(LOWER_X, -y);
    }

    public static XYChart.Data getXyChartInverseDataUpper(final Float y) {
        return new XYChart.Data(UPPER_X, -y);
    }

    public static XYChart.Data getXyChartInverseData(final Integer x, final Float y) {
        return new XYChart.Data(x, -y);
    }
}