package seedu.jxmusic.model.playlist;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_NAME_METAL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_ALIEZ;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_EXISTENCE;
import static seedu.jxmusic.testutil.TypicalPlaylists.ANIME;
import static seedu.jxmusic.testutil.TypicalPlaylists.ROCK;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.model.Playlist;
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
        assertTrue(ANIME.isSamePlaylist(ANIME));

        // null -> returns false
        assertFalse(ANIME.isSamePlaylist(null));

        // different name -> returns false
        Playlist editedAnime = new PlaylistBuilder(ANIME).withName(VALID_NAME_METAL).build();
        assertFalse(ANIME.isSamePlaylist(editedAnime));

        // same name, different tracks -> returns true
        editedAnime = new PlaylistBuilder(ANIME).withTracks(VALID_TRACK_EXISTENCE).build();
        assertTrue(ANIME.isSamePlaylist(editedAnime));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Playlist aliceCopy = new PlaylistBuilder(ANIME).build();
        assertTrue(ANIME.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ANIME.equals(ANIME));

        // null -> returns false
        assertFalse(ANIME.equals(null));

        // different type -> returns false
        assertFalse(ANIME.equals(5));

        // different playlist -> returns false
        assertFalse(ANIME.equals(ROCK));

        // different name -> returns false
        Playlist editedAlice = new PlaylistBuilder(ANIME).withName(VALID_NAME_METAL).build();
        assertFalse(ANIME.equals(editedAlice));

        // same name, different tracks -> returns false
        editedAlice = new PlaylistBuilder(ANIME).withTracks(VALID_TRACK_EXISTENCE).build();
        assertFalse(ANIME.equals(editedAlice));

        // same name, same tracks -> returns true
        editedAlice = new PlaylistBuilder(ANIME).withTracks(VALID_TRACK_ALIEZ).build();
        assertTrue(ANIME.equals(editedAlice));
    }
}
