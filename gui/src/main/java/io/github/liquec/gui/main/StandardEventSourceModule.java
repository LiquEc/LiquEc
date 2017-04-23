/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.main;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.github.liquec.analysis.core.ThreadPoolTool;
import io.github.liquec.gui.event.CommandLineEventSource;
import io.github.liquec.gui.event.ExternalEventBroker;
import io.github.liquec.gui.event.ExternalEventBrokerImpl;

public class StandardEventSourceModule extends AbstractModule {
    private final String[] args;

    public StandardEventSourceModule(final String... args) {
        this.args = args;
    }

    @Override
    protected void configure() {
        bind(CommandLineEventSource.class).toInstance(new CommandLineEventSource(args));
    }

    @Provides
    public ExternalEventBroker provideExternalEventBroker(final CommandLineEventSource commandLineEventSource, final ThreadPoolTool threadPoolTool) {
        ExternalEventBrokerImpl externalEventBroker = new ExternalEventBrokerImpl(threadPoolTool);

        commandLineEventSource.setListener(externalEventBroker);

        return externalEventBroker;
    }
}
