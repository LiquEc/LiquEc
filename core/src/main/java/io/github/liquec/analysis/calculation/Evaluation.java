/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public interface Evaluation {

    Class<? extends Range> getEvaluationClass();

    Polynomial getPolynomial();

}
