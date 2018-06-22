/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.settings;

import java.nio.file.Path;
import java.util.Optional;

public interface SettingsManager {
    Path getSessionsPath();

    void setSessionsPath(Path path);

    Optional<WindowSettings> getWindowSettings();

    void setWindowSettings(WindowSettings windowSettings);
}
