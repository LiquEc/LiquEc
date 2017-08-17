/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.chart;

public enum ChartProperties {
    SPT("CHART_COLOR_1: #9933ff;", "(N) "),
    CSR("CHART_COLOR_1: #e67300;", "(CSR) "),
    CRR("CHART_COLOR_1: #336699;", "(Crr) "),
    SF("CHART_COLOR_1: #336600;", "(SF) ");

    private String color;
    private String text;

    ChartProperties(final String color, final String text) {
        this.color = color;
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public String getText() {
        return text;
    }
}
