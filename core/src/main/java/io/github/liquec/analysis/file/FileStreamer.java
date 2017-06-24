/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.file;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.EnrichedSessionState;
import io.github.liquec.analysis.session.SessionSerialiser;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import javax.inject.Singleton;

@Singleton
public class FileStreamer {
    private static final Logger LOG = LoggerFactory.getLogger(FileStreamer.class);

    public EnrichedSessionState createNewSession() {
        return new EnrichedSessionState(new SessionState());
    }

    public EnrichedSessionState createNewSession(final Path file) {
        try {
            return SessionSerialiser.read(file);
        } catch (final LiquEcException e) {
            LOG.debug("{} is not a session file", file, e);
            throw e;
        }
    }

    public void saveSession(final Path file, final SessionState state) {
        SessionSerialiser.write(file, state);
    }

}
