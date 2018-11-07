package seedu.jxmusic.testutil;

import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_ANIME;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_CHILL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_EMPTY;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_HIPHOP;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_INSTRUMENTAL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_ROCK;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_SFX;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_PLAYLIST_NAME_TEST;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_BELL;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_HAIKEI;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_IHOJIN;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_MARBLES;
import static seedu.jxmusic.logic.commands.CommandTestUtil.VALID_TRACK_NAME_SOS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * A utility class containing a list of {@code Playlist} objects to be used in tests.
 */
public class TypicalPlaylistList {

    // Playlist's details found in {@code CommandTestUtil}
    public static final Playlist EMPTY = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_EMPTY)
            .build();
    public static final Playlist SFX = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_SFX)
            .withTracks(VALID_TRACK_NAME_SOS, VALID_TRACK_NAME_BELL, VALID_TRACK_NAME_MARBLES).build();
    public static final Playlist ANIME = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_ANIME)
            .withTracks(VALID_TRACK_NAME_HAIKEI, VALID_TRACK_NAME_IHOJIN).build();
    public static final Playlist INSTRUMENTAL = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_INSTRUMENTAL)
            .withTracks(VALID_TRACK_NAME_IHOJIN).build();
    public static final Playlist CHILL = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_CHILL)
            .withTracks(VALID_TRACK_NAME_BELL).build();
    public static final Playlist ROCK = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_ROCK)
            .withTracks(VALID_TRACK_NAME_SOS).build();
    public static final Playlist HIPHOP = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_HIPHOP)
            .withTracks(VALID_TRACK_NAME_MARBLES).build();
    public static final Playlist TEST = new PlaylistBuilder().withName(VALID_PLAYLIST_NAME_TEST).build();

    public static final List<Track> ALL_SAMPLE_TRACKS = new ArrayList<>(
            Arrays.asList(
            new TrackBuilder().withName(VALID_TRACK_NAME_MARBLES).build(),
            new TrackBuilder().withName(VALID_TRACK_NAME_IHOJIN).build(),
            new TrackBuilder().withName(VALID_TRACK_NAME_BELL).build(),
            new TrackBuilder().withName(VALID_TRACK_NAME_HAIKEI).build(),
            new TrackBuilder().withName(VALID_TRACK_NAME_SOS).build()));


    public static final String KEYWORD_MATCHING_MUSIC = "music"; // A keyword that matches name with "music"

    private TypicalPlaylistList() {} // prevents instantiation

    /**
     * Returns an {@code Library} with all the typical playlists.
     */
    public static Library getTypicalLibrary() {
        Library library = new Library();
        for (Playlist playlist : getTypicalPlaylistList()) {
            library.addPlaylist(playlist);
        }
        for (Track track : getTypicalTrackList()) {
            library.addTrack(track);
        }
        return library;
    }

    /**
     * Returns an {@code Library} with all the typical playlists after adding an additional track.
     */
    public static Library getTestPlaylistLibrary(Playlist testPlaylist) {
        Library library = new Library();
        for (Playlist playlist : getTestPlaylistList(testPlaylist)) {
            library.addPlaylist(playlist);
        }
        for (Track track : getTypicalTrackList()) {
            library.addTrack(track);
        }
        return library;
    }

    public static List<Playlist> getTypicalPlaylistList() {
        return new ArrayList<>(Arrays.asList(EMPTY, SFX, ANIME, INSTRUMENTAL, CHILL, ROCK, HIPHOP, TEST));
    }

    public static List<Playlist> getTestPlaylistList(Playlist testPlaylist) {
        ArrayList<Playlist> result = new ArrayList<>(Arrays.asList(EMPTY, SFX, ANIME, INSTRUMENTAL, CHILL, ROCK, HIPHOP));
        result.add(testPlaylist);
        return result;
    }

    public static List<Track> getTypicalTrackList() {
        return ALL_SAMPLE_TRACKS;
    }
}
