/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import com.google.inject.AbstractModule;
import io.github.liquec.analysis.core.ThreadPoolTool;
import io.github.liquec.analysis.core.ThreadPoolToolImpl;
import io.github.liquec.gui.status.StatusManager;
import io.github.liquec.gui.status.StatusManagerImpl;

public class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ThreadPoolTool.class).to(ThreadPoolToolImpl.class);
        bind(StatusManager.class).to(StatusManagerImpl.class);
    }
}
