/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.core;

public class LiquEcException extends RuntimeException {
    public LiquEcException(final String message) {
        super(message);
    }

    public LiquEcException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
