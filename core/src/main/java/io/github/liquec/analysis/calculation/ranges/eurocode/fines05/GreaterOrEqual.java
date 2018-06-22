/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.eurocode.fines05;

import io.github.liquec.analysis.calculation.Range;

public class GreaterOrEqual implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected >= 30;
        LOG.debug(":::::: Range greater or equals: " + contains);
        return contains;
    }

}
