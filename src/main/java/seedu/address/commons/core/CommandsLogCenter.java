package seedu.address.commons.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Logs down command history into a xml file. Only uses a FileHandler, does not use a ConsoleHandler.
 * Only logs command history at INFO level.
 */
public class CommandsLogCenter {
    public static final String STANDARDIZED_XML_ENCODING = "<?xml version=\"1.1\" encoding=\"UTF-8\">";
    private static final String MESSAGE_FILE_SIZE_EXCEEDED = "Maximum file size exceeded, %1$s";
    private static final int MAX_FILE_SIZE_IN_BYTES = (int) (Math.pow(2, 20) * 5); // 5MB
    private static final String LOG_FILE = "commandHistory.xml";

    private static File file = new File(LOG_FILE);
    private static boolean isLogAccessible = true;

    /**
     * Initializes, create the command log file with header if it does not exist.
     */
    public static void init() {
        try {
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file, true);
                fileWriter.append(STANDARDIZED_XML_ENCODING + "\n");
                fileWriter.close();
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        } finally {
            isLogAccessible = file.exists() && file.canWrite();
        }
    }

    /**
     * Writes the given xmlCommandEntryString to file. Throws IOException if file does not exist or cannot be written.
     */
    public static void log(String xmlCommandEntryString) throws IOException {
        if (file.length() > MAX_FILE_SIZE_IN_BYTES) {
            throw new IOException(MESSAGE_FILE_SIZE_EXCEEDED.format(String.valueOf(MAX_FILE_SIZE_IN_BYTES)));
        }
        FileWriter fileWriter = new FileWriter(file, true);
        fileWriter.append(xmlCommandEntryString + "\n");
        fileWriter.close();
    }

}
