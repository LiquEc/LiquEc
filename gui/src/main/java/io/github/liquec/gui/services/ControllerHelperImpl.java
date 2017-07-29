/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import javafx.scene.control.TextField;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerHelperImpl implements ControllerHelper {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerHelperImpl.class);

    @Override
    public void validateNumberValue(final TextField textField, final String regex, final String oldValue, final String newValue) {
        if (StringUtils.isEmpty(newValue)) {
            return;
        }
        if (!newValue.matches(regex)) {
            textField.setText(oldValue);
        }
    }

    @Override
    public void manageZerosValues(final TextField textField, final Boolean oldValue, final Boolean newValue, final String zeros, final boolean remove) {
        if (newValue) {
            return;
        }
        if (StringUtils.isEmpty(zeros)) {
            return;
        }
        if (remove) {
            this.removeZeros(textField);
        }
        this.fillZeros(textField, zeros);
    }

    @Override
    public void removeZeros(final TextField textField) {
        if (StringUtils.isEmpty(textField.getText())) {
            return;
        }
        if (textField.getText().matches("([0]|[0][0])(\\.|\\.[0]|\\.[0][0])?")) {
            textField.setText("");
        }
    }

    @Override
    public void fillZeros(final TextField textField, final String zeros) {
        if (StringUtils.isEmpty(textField.getText())) {
            return;
        }
        if (textField.getText().matches("(\\d)+[\\.]")) {
            textField.setText(textField.getText() + zeros);
        } else if (textField.getText().matches("(\\d)+")) {
            textField.setText(textField.getText() + "." + zeros);
        } else if (textField.getText().matches("(\\d)+[\\.]\\d") && zeros.length() == 2) {
            textField.setText(textField.getText() + "0");
        }
    }

    @Override
    public void trackValues(final String name, final String oldValue, final String newValue) {
        LOG.debug(name + " - oldValue: " + oldValue);
        LOG.debug(name + " - newValue: " + newValue);
    }

    @Override
    public void manageStringsValues(final TextField textField, final String oldValue, final String newValue, final int maxLength) {
        if (StringUtils.isEmpty(textField.getText())) {
            return;
        }
        LOG.debug("initial value: " + textField.getText());
        if (textField.getText().length() > maxLength) {
            textField.setText(oldValue);
        }
        textField.setText(textField.getText().toLowerCase());
        LOG.debug("final value: " + textField.getText());
    }

}
