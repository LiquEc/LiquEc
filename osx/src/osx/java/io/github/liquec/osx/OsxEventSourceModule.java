/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.osx;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.liquec.analysis.core.ThreadPoolTool;
import io.github.liquec.gui.event.CommandLineEventSource;
import io.github.liquec.gui.event.ExternalEventBroker;
import io.github.liquec.gui.event.ExternalEventBrokerImpl;

public class OsxEventSourceModule extends AbstractModule {
    private final String[] args;

    public OsxEventSourceModule(final String... args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        bind(CommandLineEventSource.class).toInstance(new CommandLineEventSource(args));
    }

    @Provides
    public ExternalEventBroker provideExternalEventBroker(final CommandLineEventSource commandLineEventSource, final OsxEventSource osxEventSource, final ThreadPoolTool threadPoolTool) {
        ExternalEventBrokerImpl externalEventBroker = new ExternalEventBrokerImpl(threadPoolTool);

        commandLineEventSource.setListener(externalEventBroker);
        osxEventSource.setListener(externalEventBroker);

        return externalEventBroker;
    }
}
