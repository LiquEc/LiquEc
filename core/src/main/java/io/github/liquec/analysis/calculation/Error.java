/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum Error {
    MAX_DEPTH_CHECK("> DEPTH (m)"),
    ABOVE_GWT("above GWT"),
    NO_LIQUEFACTION("no check"),
    LAYER_WITH_GWT_INSIDE_NOT_FOUND("error GWT"),
    LAYER_WITH_SPT_INSIDE_NOT_FOUND("error SPT");

    private String message;

    Error(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
