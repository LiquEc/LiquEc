/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import com.google.inject.AbstractModule;
import io.github.liquec.analysis.core.GuiTaskHandler;
import io.github.liquec.analysis.core.GuiTaskHandlerImpl;
import io.github.liquec.gui.dialogues.FileDialogueFactory;
import io.github.liquec.gui.services.*;
import io.github.liquec.gui.settings.SettingsManager;
import io.github.liquec.gui.settings.SettingsManagerImpl;

public class LiveGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EnvironmentManager.class).to(EnvironmentManagerImpl.class);
        bind(FileDialogueFactory.class).to(FileDialogueFactoryImpl.class);
        bind(PlacementManager.class).to(PlacementManagerImpl.class);
        bind(SettingsManager.class).to(SettingsManagerImpl.class);
        bind(WebPageTool.class).to(WebPageToolImpl.class);
        bind(GuiTaskHandler.class).to(GuiTaskHandlerImpl.class);
        bind(ControllerHelper.class).to(ControllerHelperImpl.class);
        bind(ChartHelper.class).to(ChartHelperImpl.class);
    }
}
