/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation.ranges.ncse.fines35;

import io.github.liquec.analysis.calculation.Evaluation;
import io.github.liquec.analysis.calculation.Polynomial;
import io.github.liquec.analysis.calculation.Range;

public enum NcseFines35 implements Evaluation {
    LESS_OR_EQUAL(LessOrEqual.class, Polynomial.NCSE02_F35_LESS_OR_EQUAL),
    BETWEEN(Between.class, Polynomial.NCSE02_F35_BETWEEN),
    GREATER_OR_EQUAL(GreaterOrEqual.class, Polynomial.NCSE02_F35_GREATER_OR_EQUAL);

    private Class<? extends Range> evaluation;
    private Polynomial polynomial;

    NcseFines35(final Class<? extends Range> evaluation, final Polynomial polynomial) {
        this.evaluation = evaluation;
        this.polynomial = polynomial;
    }

    public Class<? extends Range> getEvaluationClass() {
        return evaluation;
    }

    public Polynomial getPolynomial() {
        return polynomial;
    }
}
