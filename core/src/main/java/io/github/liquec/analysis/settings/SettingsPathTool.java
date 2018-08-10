/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.settings;

import io.github.liquec.analysis.core.LiquEcException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class SettingsPathTool {
    private static final String LIQUEC = "LiquEc";

    private SettingsPathTool() {
        // Prevent instantiation - all methods are static
    }

    public static Path obtainSettingsFilePath(final String filename) {
        if (SystemUtils.IS_OS_WINDOWS && StringUtils.isNotBlank(getWindowsAppHome())) {
            return Paths.get(getWindowsAppHome(), LIQUEC, filename);
        } else if (SystemUtils.IS_OS_MAC_OSX) {
            return Paths.get(getUserHome(), "Library", LIQUEC, filename);
        } else {
            return Paths.get(getUserHome(), ".LiquEc", filename);
        }
    }

    private static String getUserHome() {
        String home = System.getProperty("user.home");

        if (StringUtils.isBlank(home)) {
            throw new LiquEcException("Unable to find user home directory");
        } else {
            return home;
        }
    }

    private static String getWindowsAppHome() {
        return System.getenv("APPDATA");
    }
}
