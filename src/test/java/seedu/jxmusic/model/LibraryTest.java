package seedu.jxmusic.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import seedu.jxmusic.model.exceptions.DuplicatePlaylistException;
import seedu.jxmusic.testutil.PlaylistBuilder;
import seedu.jxmusic.testutil.TypicalPlaylistList;

public class LibraryTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final Library library = new Library();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), library.getPlaylistList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        library.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyLibrary_replacesData() {
        Library newData = TypicalPlaylistList.getTypicalLibrary();
        library.resetData(newData);
        assertEquals(newData, library);
    }

    @Test
    public void resetData_withDuplicatePlaylists_throwsDuplicatePlaylistException() {
        // Two playlists with the same identity fields
        Playlist editedAlice = new PlaylistBuilder(SFX).withTracks(VALID_TRACK_NAME_IHOJIN)
                .build();
        List<Playlist> newPlaylists = Arrays.asList(SFX, editedAlice);
        LibraryStub newData = new LibraryStub(newPlaylists);

        thrown.expect(DuplicatePlaylistException.class);
        library.resetData(newData);
    }

    @Test
    public void hasPlaylist_nullPlaylist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        library.hasPlaylist(null);
    }

    @Test
    public void hasPlaylist_playlistNotInAddressBook_returnsFalse() {
        assertFalse(library.hasPlaylist(SFX));
    }

    @Test
    public void hasPlaylist_playlistInAddressBook_returnsTrue() {
        library.addPlaylist(SFX);
        assertTrue(library.hasPlaylist(SFX));
    }

    @Test
    public void hasPlaylist_playlistWithSameIdentityFieldsInAddressBook_returnsTrue() {
        library.addPlaylist(SFX);
        Playlist editedAlice = new PlaylistBuilder(SFX).withTracks(VALID_TRACK_NAME_IHOJIN)
                .build();
        assertTrue(library.hasPlaylist(editedAlice));
    }

    @Test
    public void getPlaylistList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        library.getPlaylistList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose playlists list can violate interface constraints.
     */
    private static class LibraryStub implements ReadOnlyLibrary {
        private final ObservableList<Playlist> playlists = FXCollections.observableArrayList();
        private final ObservableSet<Track> tracks = FXCollections.observableSet(new HashSet<>());
        private final ObservableList<Track> trackList = FXCollections.observableArrayList();

        LibraryStub(Collection<Playlist> playlists) {
            this.playlists.setAll(playlists);
        }

        @Override
        public ObservableList<Playlist> getPlaylistList() {
            return playlists;
        }
        @Override
        public ObservableSet<Track> getTracks() {
            return tracks;
        }
    }

}
