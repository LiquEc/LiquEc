/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.eurocode.fines35;

import io.github.liquec.analysis.calculation.Range;

public class BetweenFirstSection implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 2 && sptCorrected <= 17;
        LOG.debug(":::::: Range between first section: " + contains);
        return contains;
    }

}
