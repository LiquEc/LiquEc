/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.crr;

import io.github.liquec.analysis.calculation.Fines;
import io.github.liquec.analysis.calculation.Range;

public class BetweenFirstSection implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > Fines.BOUND_05.getBound() && sptCorrected < Fines.BOUND_15.getBound();
        LOG.debug(":::::: Range between first section: " + contains);
        return contains;
    }

}
