/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.model.SptCalculationResult;
import io.github.liquec.analysis.session.SessionState;

public interface Runnable {
    void execute(final SessionState sessionState, final SptCalculationResult sptCalculationResult);
}
