/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.nio.file.Path;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquEcSettings {

    private Path sessionsPath;

    private WindowSettings windowSettings;

    public Path getSessionsPath() {
        return sessionsPath;
    }

    public void setSessionsPath(final Path sessionsPath) {
        this.sessionsPath = sessionsPath;
    }

    public WindowSettings getWindowSettings() {
        return windowSettings;
    }

    public void setWindowSettings(final WindowSettings windowSettings) {
        this.windowSettings = windowSettings;
    }
}
