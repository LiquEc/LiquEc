/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.eurocode.fines15;

import io.github.liquec.analysis.calculation.Range;

public class GreaterOrEqual implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected >= 24.5;
        LOG.debug(":::::: Range greater or equals: " + contains);
        return contains;
    }

}
