/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.settings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.nio.file.Path;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LiquEcSettings {
    private WindowSettings windowSettings;

    public WindowSettings getWindowSettings() {
        return windowSettings;
    }

    public void setWindowSettings(final WindowSettings windowSettings) {
        this.windowSettings = windowSettings;
    }
}
