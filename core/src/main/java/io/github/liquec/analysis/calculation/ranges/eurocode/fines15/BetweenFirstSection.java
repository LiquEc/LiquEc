/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.eurocode.fines15;

import io.github.liquec.analysis.calculation.Range;

public class BetweenFirstSection implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 5.13 && sptCorrected <= 19;
        LOG.debug(":::::: Range between first section: " + contains);
        return contains;
    }

}
