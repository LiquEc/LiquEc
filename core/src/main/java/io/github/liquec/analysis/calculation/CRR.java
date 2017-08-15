/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

import io.github.liquec.analysis.calculation.ranges.eurocode.fines35.Fines35;

import java.util.Arrays;
import java.util.Collection;

public enum CRR {
    FINES_35(Arrays.asList(Fines35.values()));

    private Collection<Enum<? extends Evaluation>> eurocodeEvaluations;

    CRR(final Collection<Enum<? extends Evaluation>> eurocodeEvaluations) {
        this.eurocodeEvaluations = eurocodeEvaluations;
    }
}
