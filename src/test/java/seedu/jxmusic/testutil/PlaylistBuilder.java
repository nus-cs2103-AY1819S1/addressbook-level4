package seedu.jxmusic.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.util.SampleDataUtil;

/**
 * A utility class to help with building Playlist objects.
 */
public class PlaylistBuilder {

    public static final String DEFAULT_NAME = "Favourites";

    private Name name;
    private List<Track> tracks;

    public PlaylistBuilder() {
        name = new Name(DEFAULT_NAME);
        tracks = new ArrayList<>();
    }

    /**
     * Initializes the PlaylistBuilder with the data of {@code playlistToCopy}.
     */
    public PlaylistBuilder(Playlist playlistToCopy) {
        name = playlistToCopy.getName();
        tracks = new ArrayList<>(playlistToCopy.getTracks());
    }

    /**
     * Sets the {@code Name} of the {@code Playlist} that we are building.
     */
    public PlaylistBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tracks} into a {@code List<Track>} and set it to the {@code Playlist} that we are building.
     */
    public PlaylistBuilder withTracks(String ... tracks) {
        this.tracks = SampleDataUtil.getTrackList(tracks);
        return this;
    }

    public Playlist build() {
        Playlist playlist = new Playlist(name);
        tracks.forEach(playlist::addTrack);
        return playlist;
    }

}
