/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import javafx.scene.control.TextField;

public interface ControllerHelper {

    void validateNumberValue(final TextField textField, final String regex, final String oldValue, final String newValue);

    void manageZerosValues(final TextField textField, final Boolean oldValue, final Boolean newValue, final String zeros, final boolean remove);

    void manageLowRangeValue(final TextField textField, final Boolean oldValue, final Boolean newValue, final String zeros, final Float lowRange, final boolean remove);

    void removeZeros(final TextField textField);

    void removeLowRangeValue(final TextField textField, final Float lowRange);

    void fillZeros(final TextField textField, final String zeros);

    void trackValues(final String name, final String oldValue, final String newValue);

    void manageStringsValues(final TextField textField, final String oldValue, final String newValue, final int maxLength);

}
