/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.settings;

public enum LiquEcDimensions {
    MIN_WIDTH(1280.0),
    MIN_HEIGHT(768.0),
    MAX_WIDTH(1280.0),
    MAX_HEIGHT(768.0);

    private double dimension;

    LiquEcDimensions(final double dimension) {
        this.dimension = dimension;
    }

    public double getDimension() {
        return dimension;
    }
}
