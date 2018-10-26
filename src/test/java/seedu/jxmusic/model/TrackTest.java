package seedu.jxmusic.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.jxmusic.testutil.Assert;

public class TrackTest {

    @Test
    public void constructor_nullTrackName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () ->
                new Track(new Name(null)));
    }

    @Test
    public void constructor_invalidTrackName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, Name.MESSAGE_NAME_CONSTRAINTS, () ->
                new Track(new Name("!@#")));
    }

    @Test
    public void constructor_trackFileNotExist_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, Track.MESSAGE_FILE_NOT_EXIST, () ->
                new Track(new Name("no track file")));
    }

    @Test
    public void constructor_trackFileNotSupported_throwsIllegalArgumentException() {
        File unsupportedFile = Paths.get(Library.LIBRARYDIR, "unsupported.mp3").toFile();
        Assert.assertThrows(IllegalArgumentException.class, Track.MESSAGE_FILE_NOT_SUPPORTED, () ->
                new Track(unsupportedFile));
    }

    @Test
    public void constructor_validTrackFile() {
        File validFile = Paths.get(Library.LIBRARYDIR, "Marbles.mp3").toFile();
        assertEquals(new Track(validFile), new Track(new Name("Marbles")));
        validFile = Paths.get(Library.LIBRARYDIR, "Service Bell Help.mp3").toFile();
        assertEquals(new Track(validFile), new Track(new Name("Service Bell Help")));
        validFile = Paths.get(Library.LIBRARYDIR, "SOS Morse Code.mp3").toFile();
        assertEquals(new Track(validFile), new Track(new Name("SOS Morse Code")));
    }

}
