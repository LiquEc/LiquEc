/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.osx.distribution;

import io.github.liquec.gui.main.CoreGuiModule;
import io.github.liquec.gui.main.LiveGuiModule;
import io.github.liquec.gui.main.LiquEcGuiExecutable;
import io.github.liquec.osx.OsxEventSourceModule;
import javafx.application.Application;

public class OsxPackagedLiquEc extends LiquEcGuiExecutable {
    public static void main(final String... args) {
        runApp(args, Application::launch, new CoreGuiModule(), new LiveGuiModule(), new OsxEventSourceModule(args));
    }
}
