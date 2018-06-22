/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.crr;

import io.github.liquec.analysis.calculation.Fines;
import io.github.liquec.analysis.calculation.Range;

public class LessOrEqual implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected <= Fines.BOUND_05.getBound();
        LOG.debug(":::::: Range less or equals [" + Fines.BOUND_05.getBound() + "]: " + contains);
        return contains;
    }

}
