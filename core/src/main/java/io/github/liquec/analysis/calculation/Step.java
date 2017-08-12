/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public interface Step {
    Class<? extends Runnable> getStepClass();
}
