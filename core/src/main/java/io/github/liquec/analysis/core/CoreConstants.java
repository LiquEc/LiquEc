/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.core;

import java.nio.charset.Charset;
import java.util.Locale;

public final class CoreConstants {
    public static final Locale LOCALE = Locale.ENGLISH;

    public static final Charset CHARSET = Charset.forName("UTF-8");

    private CoreConstants() {
        // Prevent instantiation - only constants are defined.
    }
}
