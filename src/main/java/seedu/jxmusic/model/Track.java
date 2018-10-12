package seedu.jxmusic.model;

import java.io.File;

import seedu.jxmusic.commons.util.AppUtil;
import seedu.jxmusic.commons.util.CollectionUtil;

/**
 * Represents a track in JxMusic
 * Guarantees: immutable; file is valid as declared in {@link #isValidMp3(File)}
 */
public class Track {
    public static final String MESSAGE_NAME_CONSTRAINTS =
            "Track file should be playable by javafx media";
    private final File file;
    // fileName not appended with MP3_EXTENSION
    private final String fileName;
    public static final String MP3_EXTENSION = ".mp3";

    /**
     * Constructs a {@code Track}.
     * By using a Name to restrict null or empty string
     * @param trackFileName mp3 file name of the track, does not work with ".mp3" suffix
     */
    public Track(Name trackFileName) {
        CollectionUtil.requireAllNonNull(trackFileName);
        String fileNameDotMp3;
        if (trackFileName.toString().endsWith(MP3_EXTENSION)) {
            fileNameDotMp3 = trackFileName.toString();
            fileName = removeMp3Extension(fileNameDotMp3);
        } else {
            fileName = trackFileName.toString();
            fileNameDotMp3 = fileName + MP3_EXTENSION;
        }
        File file = new File(Library.LIBRARYDIR + fileNameDotMp3);
        AppUtil.checkArgument(isValidMp3(file), MESSAGE_NAME_CONSTRAINTS);
        this.file = file;
    }

    public Track(File file) {
        CollectionUtil.requireAllNonNull(file);
        AppUtil.checkArgument(isValidMp3(file), MESSAGE_NAME_CONSTRAINTS);
        this.file = file;
        String fileNameDotMp3 = file.getName();
        fileName = removeMp3Extension(fileNameDotMp3);
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return file.getName();
    }

    /**
     * Checks for valid mp3 file
     * @param file the mp3 file to check
     * @return true if mp3 file is supported by javafx media, false otherwise
     */
    private static boolean isValidMp3(File file) {
        // todo implement method to check if mp3 file is valid so that it's playable with javafx media
        return true;
    }

    private String removeMp3Extension(String fileNameDotMp3) {
        return fileNameDotMp3.substring(0, fileNameDotMp3.length() - MP3_EXTENSION.length());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Track // instanceof handles nulls
                && fileName.equals(((Track) other).fileName)); // state check
    }

    @Override
    public int hashCode() {
        return fileName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + fileName + ']';
    }
}
