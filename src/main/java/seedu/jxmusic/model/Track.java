package seedu.jxmusic.model;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;

import seedu.jxmusic.commons.util.AppUtil;
import seedu.jxmusic.commons.util.CollectionUtil;

/**
 * Represents a track in JxMusic
 * Guarantees: immutable; file is supported by javafx media as declared in {@link #isSupported(File)}
 */
public class Track {
    public static final String MESSAGE_FILE_NOT_EXIST =
            "Track file does not exist";
    public static final String MESSAGE_FILE_NOT_SUPPORTED =
            "Track file not supported by javafx media";
    public static final String MP3_EXTENSION = ".mp3";
    private final File file;
    // fileNameWithoutExtension not appended with MP3_EXTENSION
    private final String fileNameWithoutExtension;

    /**
     * Constructs a {@code Track}.
     * By using a Name to restrict null or empty string
     * @param trackFileName mp3 file name of the track, ".mp3" suffix is optional
     */
    public Track(Name trackFileName) {
        CollectionUtil.requireAllNonNull(trackFileName);
        String fileNameDotMp3;
        if (trackFileName.toString().endsWith(MP3_EXTENSION)) {
            fileNameDotMp3 = trackFileName.toString();
            fileNameWithoutExtension = removeMp3Extension(fileNameDotMp3);
        } else {
            fileNameWithoutExtension = trackFileName.toString();
            fileNameDotMp3 = fileNameWithoutExtension + MP3_EXTENSION;
        }
        File file = new File(Library.LIBRARYDIR + fileNameDotMp3);
        AppUtil.checkArgument(file.exists(), MESSAGE_FILE_NOT_EXIST);
        AppUtil.checkArgument(isSupported(file), MESSAGE_FILE_NOT_SUPPORTED);
        this.file = file;
    }

    public Track(File file) {
        CollectionUtil.requireAllNonNull(file);
        AppUtil.checkArgument(file.exists(), MESSAGE_FILE_NOT_EXIST);
        AppUtil.checkArgument(isSupported(file), MESSAGE_FILE_NOT_SUPPORTED);
        this.file = file;
        String fileNameDotMp3 = file.getName();
        fileNameWithoutExtension = removeMp3Extension(fileNameDotMp3);
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return file.getName();
    }

    public String getFileNameWithoutExtension() {
        return fileNameWithoutExtension;
    }

    /**
     * Checks for javafx media support
     * @param file the file to check
     * @return true if file is supported by javafx media, false otherwise
     */
    private static boolean isSupported(File file) {
        // todo check by using PlayableTrack constructor in try block
        AppUtil.checkArgument(file.exists(), MESSAGE_FILE_NOT_EXIST);
        try {
            Media media = new Media(file.toURI().toString());
        } catch (MediaException e) {
            // if (e.getType() == MediaException.Type.MEDIA_UNSUPPORTED) {
            //     return false;
            // }
            return false;
        }
        return true;
    }

    private String removeMp3Extension(String fileNameDotMp3) {
        return fileNameDotMp3.substring(0, fileNameDotMp3.length() - MP3_EXTENSION.length());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Track // instanceof handles nulls
                && fileNameWithoutExtension.equals(((Track) other).fileNameWithoutExtension)); // state check
    }

    @Override
    public int hashCode() {
        return fileNameWithoutExtension.hashCode();
    }

    @Override
    public String toString() {
        return fileNameWithoutExtension;
    }
}
