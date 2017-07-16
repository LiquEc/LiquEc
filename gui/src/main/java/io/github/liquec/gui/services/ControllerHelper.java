/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import javafx.scene.control.TextField;

public interface ControllerHelper {

    void validateNumberValue(final TextField textField, final String regex, final String oldValue, final String newValue);

    void removeZeroValues(final TextField textField, final Boolean oldValue, final Boolean newValue);

    void fillWithZerosToTheLeft(final TextField textField, final Boolean oldValue, final Boolean newValue, final String zeros);

    void trackValues(final String name, final String oldValue, final String newValue);

}
