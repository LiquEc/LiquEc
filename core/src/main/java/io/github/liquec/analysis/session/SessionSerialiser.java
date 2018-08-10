/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.analysis.session;

import io.github.liquec.analysis.core.FileTool;
import io.github.liquec.analysis.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SessionSerialiser {

    private static final Logger LOG = LoggerFactory.getLogger(SessionSerialiser.class);

    private SessionSerialiser() {
        // Prevent instantiation - all methods are static
    }

    public static void write(final Path file, final SessionState state) {
        LOG.info("Writing LiquEc Session State");
        LOG.info(state.toString());
        FileTool.writeAsJson(file, state, "Unable to save file '%s'");
    }

    public static EnrichedSessionState read(final Path file) {
        return new EnrichedSessionState(readInternal(file), file);
    }

    private static SessionState readInternal(final Path file) {
        LOG.info("Reading LiquEc Session State");
        SessionState state = FileTool.readFromJson(SessionState.class, file, "Unable to load file '%s'");
        LOG.info(state.toString());
        return state;
    }

}
