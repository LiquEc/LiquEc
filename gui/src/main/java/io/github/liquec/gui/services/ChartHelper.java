/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import io.github.liquec.gui.model.SessionModel;

public interface ChartHelper {

    Double lowerBoundAxisY(final SessionModel sessionModel);

    Double getPairValueAxisX(final Double value);

    Double getPairValueAxisY(final Double value);

    Double tickUnitAxisX(final Double upperBoundAxisX);

    Double tickUnitAxisY(final Double lowerBoundAxisY);
}
