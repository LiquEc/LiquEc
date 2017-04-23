/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.core;

import java.util.concurrent.ExecutorService;


public interface ThreadPoolTool {
    ExecutorService singleDaemonExecutor(final String name);
}
