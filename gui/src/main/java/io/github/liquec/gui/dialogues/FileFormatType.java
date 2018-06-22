/*
 * Open Source Software published under the GNU Licence, Version 2.0.
 */

package io.github.liquec.gui.dialogues;

import io.github.liquec.analysis.core.LiquEcException;
import io.github.liquec.analysis.session.FileNameTool;
import javafx.stage.FileChooser.ExtensionFilter;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.liquec.analysis.core.CoreTool.listOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public enum FileFormatType {
    SESSION("LiquEc Session Files", "*" + FileNameTool.SESSION_SUFFIX);

    public static final List<FileFormatType> TYPES_SESSIONS = listOf(SESSION);

    private static final Map<ExtensionFilter, FileFormatType> types = Stream.of(FileFormatType.values())
        .collect(toMap(FileFormatType::getFilter, identity()));

    private final ExtensionFilter filter;

    FileFormatType(final String description, final String... extensions) {
        this.filter = new ExtensionFilter(description, extensions);
    }

    FileFormatType(final String description, final List<String> extensions) {
        this.filter = new ExtensionFilter(description, extensions);
    }

    public ExtensionFilter getFilter() {
        return filter;
    }

    public static FileFormatType getByFilter(final ExtensionFilter filter) {
        FileFormatType result = types.get(filter);

        if (result == null) {
            throw new LiquEcException("Unknown type " + filter.getDescription());
        } else {
            return result;
        }
    }
}
