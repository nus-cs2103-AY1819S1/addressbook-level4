package seedu.jxmusic.model;

import org.junit.Test;

import seedu.jxmusic.model.Track;
import seedu.jxmusic.testutil.Assert;
import java.io.File;

public class TrackTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Track(new File("")));
    }

    @Test
    public void constructor_invalidTrackName_throwsIllegalArgumentException() {
        Name invalidTrackName = new Name("");
        Assert.assertThrows(IllegalArgumentException.class, () -> new Track(invalidTrackName));
    }

    @Test
    public void isValidTrackName() {
        // null track name
        Assert.assertThrows(NullPointerException.class, () -> new Track(new Name("")));
    }

}
