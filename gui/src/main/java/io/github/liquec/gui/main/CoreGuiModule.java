/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import com.google.inject.AbstractModule;
import io.github.liquec.analysis.core.ThreadPoolTool;
import io.github.liquec.analysis.core.ThreadPoolToolImpl;

public class CoreGuiModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ThreadPoolTool.class).to(ThreadPoolToolImpl.class);
    }
}
