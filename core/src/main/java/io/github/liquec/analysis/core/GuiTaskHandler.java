/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.core;

public interface GuiTaskHandler {
    void executeInBackground(Runnable task);

    void executeOnGuiThread(Runnable task);

    void pauseThenExecuteOnGuiThread(Runnable task);
}
