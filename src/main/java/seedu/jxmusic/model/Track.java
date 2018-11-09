package seedu.jxmusic.model;

import static javafx.scene.media.MediaPlayer.Status.READY;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;

import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import seedu.jxmusic.commons.util.AppUtil;
import seedu.jxmusic.commons.util.CollectionUtil;

/**
 * Represents a track in JxMusic
 * Guarantees: immutable; file is supported by javafx media as declared in {@link #isSupported(File)}
 */
public class Track implements Comparable {
    public static final String MESSAGE_FILE_NOT_EXIST =
            "Track file does not exist";
    public static final String MESSAGE_FILE_NOT_SUPPORTED =
            "Track file not supported by javafx media";
    public static final String MP3_EXTENSION = ".mp3";
    private static final int[] ID3V2_HEADER = new int[]{0x49, 0x44, 0x33};
    private static final int[] MP3_HEADER = new int[]{0xff, 0xfb}; // for mp3 files without id3v2 header
    private final File file;
    // fileNameWithoutExtension not appended with MP3_EXTENSION
    private final String fileNameWithoutExtension;
    private final Duration fileDuration;
    private final Media media;

    /**
     * Constructs a {@code Track}.
     * By using a Name to restrict null or empty string
     *
     * @param trackFileName mp3 file name of the track, does not accept ".mp3" suffix
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
        media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        this.fileDuration = media.getDuration();
        mediaPlayer = null;
    }

    public Track(File file) {
        CollectionUtil.requireAllNonNull(file);
        AppUtil.checkArgument(file.exists(), MESSAGE_FILE_NOT_EXIST);
        AppUtil.checkArgument(isSupported(file), MESSAGE_FILE_NOT_SUPPORTED);
        this.file = file;
        String fileNameDotMp3 = file.getName();
        fileNameWithoutExtension = removeMp3Extension(fileNameDotMp3);
        media = new Media(file.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        while (mediaPlayer.getStatus() != READY) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.fileDuration = media.getDuration();
        mediaPlayer = null;
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

    public Duration getFileDuration() {
        return fileDuration;
    }

    public Media getMedia() {
        return media;
    }

    public String getDisplayedFileDuration() {
        int totalSeconds = (int) fileDuration.toSeconds();
        int seconds = totalSeconds % 60;
        String secondString = (seconds >= 10) ? ":" + seconds : ":0" + seconds;
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds - seconds - hours * 3600) / 60;
        String minuteString = (minutes >= 10) ? ":" + minutes : ":0" + minutes;
        return hours + minuteString + secondString;
    }

    /**
     * Checks for javafx media support and header bytes
     *
     * @param file the file to check
     * @return true if file is supported by javafx media and has valid header bytes, false otherwise
     */
    private static boolean isSupported(File file) {
        // todo check by using PlayableTrack constructor in try block
        AppUtil.checkArgument(file.exists(), MESSAGE_FILE_NOT_EXIST);
        try {
            Media media = new Media(file.toURI().toString());
            if (!hasMp3Header(file)) {
                return false;
            }
        } catch (MediaException e) {
            return false;
        }
        return true;
    }

    /**
     * Checks whether a file is truly mp3 file by looking at header bytes
     *
     * @param file the file to check
     * @return true if file has id3v2 or mp3 header bytes
     */
    private static boolean hasMp3Header(File file) {
        byte[] first3Bytes = new byte[3];
        try {
            FileInputStream fis = new FileInputStream(file);
            fis.readNBytes(first3Bytes, 0, 3);
        } catch (IOException e) {
            return false;
        }
        boolean hasId3V2 = true;
        for (int i = 0; i < ID3V2_HEADER.length; i++) {
            if (first3Bytes[i] != (byte) ID3V2_HEADER[i]) {
                hasId3V2 = false;
                break;
            }
        }
        if (hasId3V2) {
            return true;
        }
        boolean isMp3WithoutId3V2 = true;
        for (int i = 0; i < MP3_HEADER.length; i++) {
            if (first3Bytes[i] != (byte) MP3_HEADER[i]) {
                isMp3WithoutId3V2 = false;
                break;
            }
        }
        if (isMp3WithoutId3V2) {
            return true;
        }
        return false;
    }

    private String removeMp3Extension(String fileNameDotMp3) {
        return fileNameDotMp3.substring(0, fileNameDotMp3.length() - MP3_EXTENSION.length());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Track // instanceof handles nulls
                && fileNameWithoutExtension.toLowerCase().equals((
                        (Track) other).fileNameWithoutExtension.toLowerCase())); // state check
    }

    @Override
    public int hashCode() {
        return fileNameWithoutExtension.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return fileNameWithoutExtension;
    }

    @Override
    public int compareTo(Object o) {
        String otherName = ((Track) o).getFileNameWithoutExtension().toLowerCase();
        return this.getFileNameWithoutExtension().toLowerCase()
                .compareTo(otherName);
    }
}
