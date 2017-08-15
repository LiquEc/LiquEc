/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.eurocode.fines35;

import io.github.liquec.analysis.calculation.Range;

public class LessOrEqual implements Range {

    @Override
    public boolean contains(final Double sptCorrected) {
        return sptCorrected <= 2;
    }

}
