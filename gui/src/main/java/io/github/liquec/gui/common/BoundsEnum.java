/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.common;

public enum BoundsEnum {
    MAX_SPT(50.0),
    MAX_DEPTH(30.0),
    MAX_CSR(1.5),
    MAX_CRR(1.5),
    MAX_SAFETY_FACTOR(10.0);

    private double value;

    BoundsEnum(final double value) {
        this.value = value;
    }

    public double getPositiveValue() {
        return value;
    }

    public double getNegativeValue() {
        return -value;
    }

}
