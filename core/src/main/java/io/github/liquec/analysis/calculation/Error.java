/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.calculation;

public enum Error {
    NO_CHECK("> 20 (m)"),
    ABOVE_GWT("Above GWT");

    private String message;

    Error(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
