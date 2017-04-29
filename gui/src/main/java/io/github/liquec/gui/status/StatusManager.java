/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.status;

public interface StatusManager {
    boolean beginExit();

    void markSuccess();

    void completeAction();
}
