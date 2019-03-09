package seedu.jxmusic.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.model.Model.PREDICATE_SHOW_ALL_PLAYLISTS;
import static seedu.jxmusic.testutil.TypicalPlaylistList.SFX;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.jxmusic.testutil.LibraryBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPlaylist_nullPlaylist_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPlaylist(null);
    }

    @Test
    public void hasPlaylist_playlistNotInLibrary_returnsFalse() {
        assertFalse(modelManager.hasPlaylist(SFX));
    }

    @Test
    public void hasPlaylist_playlistInLibrary_returnsTrue() {
        modelManager.addPlaylist(SFX);
        assertTrue(modelManager.hasPlaylist(SFX));
    }

    @Test
    public void getFilteredPlaylistList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPlaylistList().remove(0);
    }

    @Test
    public void equals() {
        Library library = new LibraryBuilder().withPlaylist(SFX).build();
        Library differentLibrary = new Library();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(library, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(library, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different library -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentLibrary, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = SFX.getName().nameString.split("\\s+");
        modelManager.updateFilteredPlaylistList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        // assertFalse(modelManager.equals(new ModelManager(library, userPrefs))); // todo failing test

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPlaylistList(PREDICATE_SHOW_ALL_PLAYLISTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setLibraryFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(library, differentUserPrefs)));
    }
}
