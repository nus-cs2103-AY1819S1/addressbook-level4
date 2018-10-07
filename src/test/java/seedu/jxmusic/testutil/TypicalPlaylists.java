package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_METAL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRACK_EXISTENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TRACK_ALIEZ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Library;
import seedu.address.model.Playlist;

/**
 * A utility class containing a list of {@code Playlist} objects to be used in tests.
 */
public class TypicalPlaylists {

    public static final Playlist ANIME = new PlaylistBuilder().withName("Anime OST")
            .withTracks("aliez").build();
    public static final Playlist METAL = new PlaylistBuilder().withName("Metal")
            .withTracks("EXiSTENCE").build();
    public static final Playlist POP = new PlaylistBuilder().withName("Pop songs").build();
    public static final Playlist ROCK = new PlaylistBuilder().withName("ROCK N ROLL")
            .withTracks("Bohemian Rhapsody").build();
    public static final Playlist CHINESE = new PlaylistBuilder().withName("Chinese songs")
            .withTracks("Shen Hua").build();
    public static final Playlist CLASSICS = new PlaylistBuilder().withName("Classics")
            .withTracks("Canon").build();
    public static final Playlist INSTRUMENTAL = new PlaylistBuilder().withName("instrumental")
            .withTracks("Fur Elise").build();

    // Manually added
    public static final Playlist HOON = new PlaylistBuilder().withName("Hoon Meier").build();
    public static final Playlist IDA = new PlaylistBuilder().withName("Ida Mueller").build();

    // Manually added - Playlist's details found in {@code CommandTestUtil}
    public static final Playlist AMY = new PlaylistBuilder().withName(VALID_NAME_AMY)
            .withTracks(VALID_TRACK_EXISTENCE).build();
    public static final Playlist BOB = new PlaylistBuilder().withName(VALID_NAME_METAL)
            .withTracks(VALID_TRACK_ALIEZ, VALID_TRACK_EXISTENCE).build();

    public static final String KEYWORD_MATCHING_SONG = "song"; // A keyword that matches name with "song"

    private TypicalPlaylists() {} // prevents instantiation

    /**
     * Returns an {@code Library} with all the typical playlists.
     */
    public static Library getTypicalLibrary() {
        Library ab = new Library();
        for (Playlist playlist : getTypicalPlaylists()) {
            ab.addPlaylist(playlist);
        }
        return ab;
    }

    public static List<Playlist> getTypicalPlaylists() {
        return new ArrayList<>(Arrays.asList(ANIME, METAL, POP, ROCK, CHINESE, CLASSICS, INSTRUMENTAL));
    }
}
