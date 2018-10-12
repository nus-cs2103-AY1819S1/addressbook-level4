package seedu.jxmusic.model;

import org.junit.Test;

import seedu.jxmusic.model.track.Track;
import seedu.jxmusic.testutil.Assert;

public class TrackTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Track(null));
    }

    @Test
    public void constructor_invalidTrackName_throwsIllegalArgumentException() {
        String invalidTrackName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Track(invalidTrackName));
    }

    @Test
    public void isValidTrackName() {
        // null track name
        Assert.assertThrows(NullPointerException.class, () -> Track.(null));
    }

}
