/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import javafx.scene.chart.XYChart;

public class InverseData {

    public static XYChart.Data getXYChartInverseData(final Float x, final Float y) {
        return new XYChart.Data(x, y * -1);
    }

    public static XYChart.Data getXYChartInverseData(final Integer x, final Float y) {
        return new XYChart.Data(x, y * -1);
    }
}
