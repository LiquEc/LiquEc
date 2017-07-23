/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

import javafx.scene.chart.XYChart;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class LiquEcData {

    public static Float LOWER_X = -5.0f;
    public static Float UPPER_X = 55.0f;

    public static XYChart.Data<Number, Number> getChartInverseDataLowerX(final Float y) {
        return new XYChart.Data<>(LOWER_X, -y);
    }

    public static XYChart.Data<Number, Number> getChartInverseDataUpperX(final Float y) {
        return new XYChart.Data<>(UPPER_X, -y);
    }

    public static XYChart.Data<Number, Number> getChartInverseDataSptPoint(final Integer x, final Float y) {
        XYChart.Data data=  new XYChart.Data<>(x, -y);
        Circle circle = new Circle(3.5);
        circle.setFill(Color.DARKCYAN);
        data.setNode(circle);
        return data;
    }
    public static XYChart.Data<Number, Number> getChartDataSptPointLine(final Float x, final Float y) {
        XYChart.Data data=  new XYChart.Data<>(x, y);
        Circle circle = new Circle(1);
        circle.setFill(Color.DARKCYAN);
        data.setNode(circle);
        return data;
    }
}
