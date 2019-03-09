package seedu.jxmusic.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertCardDisplaysTrack;

import org.junit.Test;

import guitests.guihandles.TrackCardHandle;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.testutil.TrackBuilder;

public class TrackCardTest extends GuiUnitTest {
    @Test
    public void display() {
        Track track = new TrackBuilder().build();
        TrackCard trackCard = new TrackCard(track, 1);
        uiPartRule.setUiPart(trackCard);
        assertCardDisplay(trackCard, track, track.getFileNameWithoutExtension());
    }

    @Test
    public void equals() {

        Track track = new TrackBuilder().build();
        TrackCard trackCard = new TrackCard(track, 0);

        // same track, same index -> returns true
        TrackCard copy = new TrackCard(track, 0);
        assertTrue(trackCard.equals(copy));

        // same object -> returns true
        assertTrue(trackCard.equals(trackCard));

        // null -> returns false
        assertFalse(trackCard.equals(null));

        // different types -> returns false
        assertFalse(trackCard.equals(0));

        // different track, same index -> returns false
        Track differentTrack = new TrackBuilder().withName("Marbles").build();
        assertFalse(trackCard.equals(new TrackCard(differentTrack, 0)));

        // same track, different index -> returns false
        // assertFalse(trackCard.equals(new TrackCard(track, 1)));
    }

    /**
     * Asserts that {@code trackCard} displays the details of {@code expectedTrackCard} correctly and matches
     * {@code expectedFileName}.
     */
    private void assertCardDisplay(TrackCard trackCard, Track expectedTrack, String expectedFileName) {
        guiRobot.pauseForHuman();

        TrackCardHandle trackCardHandle = new TrackCardHandle(trackCard.getRoot());

        // verify file name is displayed correctly
        assertEquals(expectedFileName, expectedTrack.getFileNameWithoutExtension());

        // verify track details are displayed correctly
        assertCardDisplaysTrack(expectedTrack, trackCardHandle);
    }
}
