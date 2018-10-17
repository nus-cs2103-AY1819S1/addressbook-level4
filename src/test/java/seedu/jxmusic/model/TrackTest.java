package seedu.jxmusic.model;

import org.junit.Test;

import seedu.jxmusic.testutil.Assert;
import java.io.File;
import java.nio.file.Paths;

public class TrackTest {

    @Test
    public void constructor_nullTrackName_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class,
                () -> new Track(new Name(null)));
    }

    @Test
    public void constructor_invalidTrackName_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, Name.MESSAGE_NAME_CONSTRAINTS,
                () -> new Track(new Name("!@#")));
    }

    @Test
    public void constructor_trackFileNotExist_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, Track.MESSAGE_FILE_NOT_EXIST,
                () -> new Track(new Name("no track file")));
    }

    @Test
    public void constructor_trackFileNotSupported_throwsIllegalArgumentException() {
        File unsupportedFile = Paths.get(Library.LIBRARYDIR, "unsupported.txt").toFile();
        Assert.assertThrows(IllegalArgumentException.class, Track.MESSAGE_FILE_NOT_SUPPORTED,
                () -> new Track(unsupportedFile));
    }

}
