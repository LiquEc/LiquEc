/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.gui.services;

import io.github.liquec.analysis.file.FileStreamer;
import io.github.liquec.analysis.session.EnrichedSessionState;

import java.nio.file.Path;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionFileService {

    private final FileStreamer streamer;

    @Inject
    public SessionFileService(final FileStreamer streamer) {
        this.streamer = streamer;
    }

    public EnrichedSessionState createNewSession() {
        return streamer.createNewSession();
    }

    public EnrichedSessionState createNewSession(final Path file) {
        return streamer.createNewSession(file);
    }
}
