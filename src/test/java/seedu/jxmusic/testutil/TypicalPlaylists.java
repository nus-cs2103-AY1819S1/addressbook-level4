package seedu.jxmusic.testutil;

import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_NAME_ARG_EXISTENCE;
import static seedu.jxmusic.logic.commands.CommandTestUtil.TRACK_NAME_ARG_MYDEMONS;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_ANIME;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_EMPTY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_INSTRUMENTAL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_METAL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_FURELISE;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_HAIKEI;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;

/**
 * A utility class containing a list of {@code Playlist} objects to be used in tests.
 */
public class TypicalPlaylists {

    public static final Playlist POP = new PlaylistBuilder().withName("Pop songs").build();
    public static final Playlist ROCK = new PlaylistBuilder().withName("ROCK N ROLL")
            .withTracks("Bohemian Rhapsody").build();
    public static final Playlist CHINESE = new PlaylistBuilder().withName("Chinese songs")
            .withTracks("Shen Hua").build();

    // Manually added
    public static final Playlist FAVOURITES = new PlaylistBuilder().withName("Faves").build();
    public static final Playlist ENGLISH = new PlaylistBuilder().withName("English").build();

    // Manually added - Playlist's details found in {@code CommandTestUtil}
    public static final Playlist EMPTY = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_EMPTY)
            .build();
    public static final Playlist ANIME = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_ANIME)
            .withTracks(VALID_TRACK_NAME_HAIKEI, VALID_TRACK_NAME_IHOJIN).build();
    public static final Playlist INSTRUMENTAL = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_INSTRUMENTAL)
            .withTracks(VALID_TRACK_NAME_IHOJIN, VALID_TRACK_NAME_FURELISE).build();
    public static final Playlist METAL = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_METAL)
            .withTracks(TRACK_NAME_ARG_EXISTENCE, TRACK_NAME_ARG_MYDEMONS).build();

    public static final String KEYWORD_MATCHING_SONG = "song"; // A keyword that matches name with "song"

    private TypicalPlaylists() {} // prevents instantiation

    /**
     * Returns an {@code Library} with all the typical playlists.
     */
    public static Library getTypicalLibrary() {
        Library library = new Library();
        for (Playlist playlist : getTypicalPlaylists()) {
            library.addPlaylist(playlist);
        }
        return library;
    }

    public static List<Playlist> getTypicalPlaylists() {
        return new ArrayList<>(Arrays.asList(ANIME, POP, ROCK, CHINESE, INSTRUMENTAL, METAL));
    }
}
