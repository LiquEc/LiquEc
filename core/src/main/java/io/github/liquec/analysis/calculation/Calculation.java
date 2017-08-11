/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.session.ResultState;
import io.github.liquec.analysis.session.SessionState;

public interface Calculation {
    void execute(final SessionState sessionState, final ResultState resultState);
}
