/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.crr;

import io.github.liquec.analysis.calculation.Evaluation;
import io.github.liquec.analysis.calculation.Range;
import io.github.liquec.analysis.calculation.ranges.eurocode.fines35.EurocodeFines35;
import io.github.liquec.analysis.calculation.ranges.ncse.fines35.NcseFines35;

import java.util.Arrays;
import java.util.Collection;

public enum CRR {
    GREATER_OR_EQUAL(GreaterOrEqual.class, new java.util.List[]{Arrays.asList(EurocodeFines35.values()), null}, new java.util.List[]{Arrays.asList(NcseFines35.values()), null});

    private Class<? extends Range> crrEvaluation;
    private Collection<Enum<? extends Evaluation>>[] eurocodeEvaluations;
    private Collection<Enum<? extends Evaluation>>[] nsceEvaluations;

    CRR(final Class<? extends Range> crrEvaluation,
        final Collection<Enum<? extends Evaluation>> eurocodeEvaluations[],
        final Collection<Enum<? extends Evaluation>> nsceEvaluations[]) {
        this.crrEvaluation = crrEvaluation;
        this.eurocodeEvaluations = eurocodeEvaluations;
        this.nsceEvaluations = nsceEvaluations;
    }

    public Class<? extends Range> getCrrEvaluation() {
        return crrEvaluation;
    }

    public Collection<Enum<? extends Evaluation>>[] getEurocodeEvaluations() {
        return eurocodeEvaluations;
    }

    public Collection<Enum<? extends Evaluation>>[] getNsceEvaluations() {
        return nsceEvaluations;
    }
}
