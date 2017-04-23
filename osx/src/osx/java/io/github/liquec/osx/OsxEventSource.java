/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.osx;

import com.apple.eawt.Application;
import io.github.liquec.gui.event.ExternalEventListener;
import io.github.liquec.gui.event.SingleExternalEventSource;

import javax.inject.Singleton;

@Singleton
public class OsxEventSource implements SingleExternalEventSource {
    @Override
    public void setListener(final ExternalEventListener listener) {
        Application application = Application.getApplication();

        application.setOpenFileHandler(new OsxOpenFilesHandler(listener));
    }
}
