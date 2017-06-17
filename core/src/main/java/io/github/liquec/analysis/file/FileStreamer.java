/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.file;

import io.github.liquec.analysis.model.CalculationData;
import io.github.liquec.analysis.session.EnrichedSessionState;
import io.github.liquec.analysis.session.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.UUID;
import javax.inject.Singleton;



@Singleton
public class FileStreamer {
    private static final Logger LOG = LoggerFactory.getLogger(FileStreamer.class);

    public EnrichedSessionState createNewSession() {
        return new EnrichedSessionState(new SessionState());
    }

    public EnrichedSessionState createNewSession(final Path file) {
        // parse file json to model
        // CalculationData model = analyse(file);
        CalculationData model = fakeAnalyse(file);
        return new EnrichedSessionState(new SessionState(model), file);
    }

    private CalculationData analyse(final Path file) {
        return new CalculationData(null);
    }

    private CalculationData fakeAnalyse(final Path file) {
        return new CalculationData(UUID.randomUUID().toString());
    }

}
