/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.crr;

import io.github.liquec.analysis.calculation.Range;

public class GreaterOrEqual implements Range {

    @Override
    public boolean contains(final Double finesContent) {
        final boolean contains = finesContent.doubleValue() >= 35;
        LOG.debug(":::::: Range greater or equals: " + contains);
        return contains;
    }

}
