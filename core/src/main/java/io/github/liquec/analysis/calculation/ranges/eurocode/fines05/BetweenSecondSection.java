/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.eurocode.fines05;

import io.github.liquec.analysis.calculation.Range;

public class BetweenSecondSection implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 24.9 && sptCorrected < 30;
        LOG.debug(":::::: Range between second section: " + contains);
        return contains;
    }

}
