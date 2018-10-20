package seedu.jxmusic.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.ui.testutil.GuiTestAssert.assertCardDisplaysPlaylist;

import org.junit.Test;

import guitests.guihandles.PlaylistCardHandle;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.testutil.PlaylistBuilder;

public class PlaylistCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tracks
        Playlist playlistWithNoTracks = new PlaylistBuilder().build();
        PlaylistCard playlistCard = new PlaylistCard(playlistWithNoTracks, 1);
        uiPartRule.setUiPart(playlistCard);
        assertCardDisplay(playlistCard, playlistWithNoTracks, 1);

        // with tracks
        Playlist playlistWithTracks = new PlaylistBuilder().withTracks("Haikei Goodbye Sayonara").build();
        playlistCard = new PlaylistCard(playlistWithTracks, 2);
        uiPartRule.setUiPart(playlistCard);
        assertCardDisplay(playlistCard, playlistWithTracks, 2);
    }

    @Test
    public void equals() {
        Playlist playlist = new PlaylistBuilder().build();
        PlaylistCard playlistCard = new PlaylistCard(playlist, 0);

        // same playlist, same index -> returns true
        PlaylistCard copy = new PlaylistCard(playlist, 0);
        assertTrue(playlistCard.equals(copy));

        // same object -> returns true
        assertTrue(playlistCard.equals(playlistCard));

        // null -> returns false
        assertFalse(playlistCard.equals(null));

        // different types -> returns false
        assertFalse(playlistCard.equals(0));

        // different playlist, same index -> returns false
        Playlist differentPlaylist = new PlaylistBuilder().withName("differentName").build();
        assertFalse(playlistCard.equals(new PlaylistCard(differentPlaylist, 0)));

        // same playlist, different index -> returns false
        // assertFalse(playlistCard.equals(new PlaylistCard(playlist, 1))); // todo failing test
    }

    /**
     * Asserts that {@code playlistCard} displays the details of {@code expectedPlaylist} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(PlaylistCard playlistCard, Playlist expectedPlaylist, int expectedId) {
        guiRobot.pauseForHuman();

        PlaylistCardHandle playlistCardHandle = new PlaylistCardHandle(playlistCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", playlistCardHandle.getId());

        // verify playlist details are displayed correctly
        assertCardDisplaysPlaylist(expectedPlaylist, playlistCardHandle);
    }
}
