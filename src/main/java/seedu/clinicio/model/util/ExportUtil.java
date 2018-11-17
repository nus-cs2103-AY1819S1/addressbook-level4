package seedu.clinicio.model.util;

import static seedu.clinicio.commons.util.CollectionUtil.requireAllNonNull;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Contains utility methods for exporting data.
 * @@author arsalanc-v2
 */
public class ExportUtil {

    private static final String LINE_INVALID = "Line is not valid.";
    private static final String LINES_INVALID = "List of lines is not valid.";

    /**
     * Writes a single line to a file.
     * @param writer The FileWriter object used to write to a file.
     * @param line The line to be written to the file, a String.
     * @throws IOException
     * @throws IllegalArgumentException if {@code line} is not valid.
     */
    public static void writeLine(FileWriter writer, String line) throws IOException,
        IllegalArgumentException {
        requireAllNonNull(writer, line);

        if (!isValidLine(line)) {
            throw new IllegalArgumentException(LINE_INVALID);
        }

        writer.append(line + "\n");
        writer.flush();
        writer.close();
    }

    /**
     * Writes one or more lines to a file.
     * @param writer The FileWriter object used to write to a file.
     * @param lines The lines to be written to the file, each a String in an ArrayList.
     * @throws IOException
     * @throws NullPointerException if {@code lines} is null.
     * @throws IllegalArgumentException if {@code lines} is empty.
     */
    public static void writeLines(FileWriter writer, List<String> lines) throws IOException,
        NullPointerException, IllegalArgumentException {
        requireAllNonNull(writer, lines);

        if (lines.size() < 1) {
            throw new IllegalArgumentException(LINES_INVALID);
        }

        int numLines = lines.size();
        for (int i = 0; i < numLines; i++) {
            String line = lines.get(i);
            if (!isValidLine(line)) {
                throw new IllegalArgumentException(LINE_INVALID);
            }

            writer.append(line + "\n");
        }

        writer.flush();
        writer.close();
    }

    /**
     * Checks if a string is a valid line.
     * @param line A String.
     * @return {@code true} if {@code line} is valid. {@code false} otherwise.
     */
    public static boolean isValidLine(String line) {
        Objects.requireNonNull(line);
        return getNumberOfLines(line) == 1;
    }

    /**
     * Returns the number of lines in a string.
     * Assumes newline character as separator.
     * @param str
     * @return An integer.
     */
    public static int getNumberOfLines(String str) {
        String[] allLines = str.split("\n");
        return allLines.length;
    }
}
