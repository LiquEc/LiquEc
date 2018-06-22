/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.ncse.fines35;

import io.github.liquec.analysis.calculation.Range;

public class Between implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        final boolean contains = sptCorrected > 2.82 && sptCorrected < 20.7;
        LOG.debug(":::::: Range between first section: " + contains);
        return contains;
    }

}
