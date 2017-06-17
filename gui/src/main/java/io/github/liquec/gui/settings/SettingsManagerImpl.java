/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.settings;

import io.github.liquec.analysis.settings.BaseSettingsManager;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.inject.Singleton;

@Singleton
public class SettingsManagerImpl extends BaseSettingsManager<LiquEcSettings> implements SettingsManager {
    public static final String SETTINGS_JSON = "settings.json";

    public SettingsManagerImpl() {
        super(SETTINGS_JSON, LiquEcSettings.class);
    }

    public SettingsManagerImpl(final Path settingsFile) {
        super(settingsFile, LiquEcSettings.class);
    }

    @Override
    public Path getSessionsPath() {
        return getPath(LiquEcSettings::getSessionsPath);
    }

    @Override
    public void setSessionsPath(final Path path) {
        setPath(LiquEcSettings::setSessionsPath, path);
    }

    @Override
    public Optional<WindowSettings> getWindowSettings() {
        WindowSettings value = getValue(LiquEcSettings::getWindowSettings);

        return Optional.ofNullable(value);
    }

    @Override
    public void setWindowSettings(final WindowSettings windowSettings) {
        setValue(LiquEcSettings::setWindowSettings, windowSettings);
    }

    private <T> T getValue(final Function<LiquEcSettings, T> getter) {
        LiquEcSettings settings = readSettings();

        return getter.apply(settings);
    }

    private <T> void setValue(final BiConsumer<LiquEcSettings, T> setter, final T value) {
        LiquEcSettings settings = readSettings();

        setter.accept(settings, value);
        writeSettings(settings);
    }

    private Path getPath(final Function<LiquEcSettings, Path> getter) {
        LiquEcSettings settings = readSettings();
        Path path = getter.apply(settings);

        if (path != null && Files.isDirectory(path)) {
            return path;
        }

        return Paths.get(System.getProperty("user.home"));
    }

    private void setPath(final BiConsumer<LiquEcSettings, Path> setter, final Path path) {
        LiquEcSettings settings = readSettings();

        setter.accept(settings, path);
        writeSettings(settings);
    }
}
