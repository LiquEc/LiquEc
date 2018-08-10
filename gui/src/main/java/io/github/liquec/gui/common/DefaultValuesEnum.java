/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.common;

public enum DefaultValuesEnum {
    EARTHQUAKE_MAGNITUDE("7.5"),
    ENERGY_RATIO("60.0"),
    COEFFICIENT_OF_CONTRIBUTION("1.0");

    private String value;

    DefaultValuesEnum(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
