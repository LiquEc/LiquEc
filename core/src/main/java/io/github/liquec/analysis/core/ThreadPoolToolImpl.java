/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.core;

import javax.inject.Singleton;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Singleton
public class ThreadPoolToolImpl implements ThreadPoolTool {
    @Override
    public ExecutorService singleDaemonExecutor(final String name) {
        return Executors.newFixedThreadPool(1, r -> newDaemonThread(r, name));
    }

    private Thread newDaemonThread(final Runnable r, final String name) {
        Thread thread = new Thread(r);

        thread.setDaemon(true);
        thread.setName(name);

        return thread;
    }
}
