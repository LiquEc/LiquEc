/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import com.google.inject.AbstractModule;
import io.github.liquec.gui.services.EnvironmentManager;
import io.github.liquec.gui.services.EnvironmentManagerImpl;
import io.github.liquec.gui.services.PlacementManager;
import io.github.liquec.gui.services.PlacementManagerImpl;
import io.github.liquec.gui.settings.SettingsManager;
import io.github.liquec.gui.settings.SettingsManagerImpl;

public class LiveGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EnvironmentManager.class).to(EnvironmentManagerImpl.class);
        bind(PlacementManager.class).to(PlacementManagerImpl.class);
        bind(SettingsManager.class).to(SettingsManagerImpl.class);
    }
}
