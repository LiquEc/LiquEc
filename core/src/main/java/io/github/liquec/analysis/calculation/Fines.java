/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum Fines {
    BOUND_05(5.0),
    BOUND_15(15.0),
    BOUND_35(35.0);

    private double bound;

    Fines(final double bound) {
        this.bound = bound;
    }

    public double getBound() {
        return bound;
    }
}
