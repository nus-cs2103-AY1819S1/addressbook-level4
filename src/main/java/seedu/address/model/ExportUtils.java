package seedu.address.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Encapsulates methods required for data export functionality.
 * @@author arsalanc-v2
 */
public class ExportUtils {

    /**
     * Writes a single line to a file.
     * @param writer The FileWriter object used to write to a file.
     * @param line The line to be written to the file, a String.
     * @throws IOException
     * @throws IllegalArgumentException if {@code line} is not valid.
     */
    public static void writeLine(FileWriter writer, String line) throws IOException,
        IllegalArgumentException {

        if (!isValidLine(line)) {
            throw new IllegalArgumentException();
        }

        writer.append(line);
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
    public static void writeLines(FileWriter writer, ArrayList<String> lines) throws IOException,
        NullPointerException, IllegalArgumentException {
        if (lines == null) {
            throw new NullPointerException();
        }

        int numLines = lines.size();
        if (numLines < 1) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (!isValidLine(line)) {
                throw new IllegalArgumentException();
            }

            // add a newline character at the end of every line except the last.
            if (i < numLines - 1) {
                line = line + "\n";
            }

            writer.append(line);
        }

        writer.flush();
        writer.close();
    }

    /**
     * Checks if a string is a valid line.
     * @param line
     * @return true if line is valid. False otherwise.
     */
    public static boolean isValidLine(String line) {
        if (line == null) {
            return false;
        }

        if (getNumberOfLines(line) != 1) {
            return false;
        }

        return true;
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
