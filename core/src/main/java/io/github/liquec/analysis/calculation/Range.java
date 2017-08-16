/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Range {
    Logger LOG = LoggerFactory.getLogger(Range.class);
    boolean contains(final Double value);
}
