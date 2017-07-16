/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHelperImpl implements ControllerHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerHelperImpl.class);

    @Override
    public void validateNumberValue(final TextField textField, final String regex, final String oldValue, final String newValue) {
        if (!newValue.matches(regex)) {
            textField.setText(oldValue);
        }
    }

    @Override
    public void removeZeroValues(final TextField textField, final Boolean oldValue, final Boolean newValue) {
        if (newValue) return;
        if (textField.getText().matches("([0]|[0][0])(\\.|\\.[0]|\\.[0][0])?")) {
            textField.setText("");
        }
    }

    @Override
    public void fillWithZerosToTheLeft(final TextField textField, final Boolean oldValue, final Boolean newValue, final String zeros) {
        if (newValue) return;
        if (textField.getText().matches("(\\d)+[\\.]")) {
            textField.setText(textField.getText() + zeros);
        }
    }

    @Override
    public void trackValues(final String name, final String oldValue, final String newValue) {
        LOG.debug(name + " old value: " + oldValue);
        LOG.debug(name + " new value: " + newValue);
    }

}
