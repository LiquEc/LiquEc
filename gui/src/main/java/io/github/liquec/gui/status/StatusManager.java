/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.status;

import java.nio.file.Path;

public interface StatusManager {
    boolean beginExit();

    boolean beginAbout();

    boolean beginNewSession();

    boolean beginOpenSession();

    boolean beginSaveSession();

    void markSuccess();

    void completeAction();

    void performAction();

    void performAction(Path file);

}
