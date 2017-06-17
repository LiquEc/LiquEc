/*
 * Open Source Software published under the Apache Licence, Version 2.0.
 */

package io.github.liquec.analysis.file;

import io.github.liquec.analysis.core.LiquEcException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public final class TikaTool {
    private TikaTool() {
        // Prevent instantiation - all methods are static
    }

    public static String read(final Path file) {
        Metadata metadata = new Metadata();

        try (InputStream in = TikaInputStream.get(file, metadata))  {
            Tika tika = new Tika();

            return tika.parseToString(in, metadata, -1);
        } catch (IOException | TikaException e) {
            throw new LiquEcException(String.format("Unable to read file '%s'", file), e);
        }
    }
}
