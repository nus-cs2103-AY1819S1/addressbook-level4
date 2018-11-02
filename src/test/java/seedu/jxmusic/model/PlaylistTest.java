package seedu.jxmusic.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_ANIME;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_BELL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_HAIKEI;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_SOS;
import static seedu.jxmusic.testutil.TypicalPlaylistList.ANIME;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.testutil.PlaylistBuilder;

public class PlaylistTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Playlist playlist = new PlaylistBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        playlist.getTracks().remove(0);
    }

    @Test
    public void isSamePlaylist() {
        // same object -> returns true
        assertTrue(SFX.isSamePlaylist(SFX));

        // null -> returns false
        assertFalse(SFX.isSamePlaylist(null));

        // different name -> returns false
        Playlist editedSfx = new PlaylistBuilder(SFX).withName(VALID_PLAYLIST_NAME_ANIME).build();
        assertFalse(SFX.isSamePlaylist(editedSfx));

        // same name, different tracks -> returns true
        editedSfx = new PlaylistBuilder(SFX).withTracks(VALID_TRACK_NAME_HAIKEI).build();
        assertTrue(SFX.isSamePlaylist(editedSfx));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Playlist aliceCopy = new PlaylistBuilder(SFX).build();
        assertTrue(SFX.equals(aliceCopy));

        // same object -> returns true
        assertTrue(SFX.equals(SFX));

        // null -> returns false
        assertFalse(SFX.equals(null));

        // different type -> returns false
        assertFalse(SFX.equals(5));

        // different playlist -> returns false
        assertFalse(SFX.equals(ANIME));

        // different name -> returns false
        Playlist editedAlice = new PlaylistBuilder(SFX).withName(VALID_PLAYLIST_NAME_ANIME).build();
        assertFalse(SFX.equals(editedAlice));

        // same name, different tracks -> returns false
        editedAlice = new PlaylistBuilder(SFX).withTracks(VALID_TRACK_NAME_IHOJIN).build();
        assertFalse(SFX.equals(editedAlice));

        // same name, same tracks -> returns true
        editedAlice = new PlaylistBuilder(SFX)
                .withTracks(VALID_TRACK_NAME_SOS, VALID_TRACK_NAME_BELL, VALID_TRACK_NAME_MARBLES).build();
        assertTrue(SFX.equals(editedAlice));
    }
}
