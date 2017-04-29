/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.settings;

import java.util.Optional;

public interface SettingsManager {
    Optional<WindowSettings> getWindowSettings();

    void setWindowSettings(WindowSettings windowSettings);
}
