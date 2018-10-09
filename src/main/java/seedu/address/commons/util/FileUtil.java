package seedu.address.commons.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Checks if two files are identical.
     * @@author arsalanc-v2
     * @param file1
     * @param file2
     * @return true if file1 and file2 are identical in content. false otherwise.
     * @throws IOException
     */
    public static boolean areIdenticalFiles(File file1, File file2) throws IOException {
        FileInputStream inputStream1 = new FileInputStream(file1);
        FileInputStream inputStream2 = new FileInputStream(file2);

        BufferedInputStream bufferedStream1 = new BufferedInputStream(inputStream1);
        BufferedInputStream bufferedStream2 = new BufferedInputStream(inputStream2);

        int fileChar1 = bufferedStream1.read();
        while (fileChar1 != -1) {
            int fileChar2 = bufferedStream2.read();
            if (fileChar1 != fileChar2) {
                inputStream1.close();
                inputStream2.close();
                return false;
            }

            fileChar1 = bufferedStream1.read();
        }

        // check if file1 is a subset of file2 in terms of content AND file 2 is longer
        if (bufferedStream2.read() != -1) {
            inputStream1.close();
            inputStream2.close();
            return false;
        }

        inputStream1.close();
        inputStream2.close();
        return true;
    }
}
