package seedu.jxmusic.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.Track;

/**
 * Contains utility methods for populating {@code Library} with sample data.
 */
public class SampleDataUtil {
    public static Track[] getSampleTracks() {
        return new Track[] {
            new Track(new Name("aliez")),
            new Track(new Name("acyort")),
            new Track(new Name("scarborough fair")),
            new Track(new Name("Ihojin no Yaiba"))
        };
    }

    public static Playlist[] getSamplePlaylists() {
        return new Playlist[] {
            new Playlist(new Name("Aldnoah Zero"), getTrackList("aliez", "acyort")),
            new Playlist(new Name("Favourites"), getTrackList("scarborough fair", "Ihojin no Yaiba"))
        };
    }

    public static ReadOnlyLibrary getSampleLibrary() {
        Library sampleAb = new Library();
        for (Playlist samplePlaylist : getSamplePlaylists()) {
            sampleAb.addPlaylist(samplePlaylist);
        }
        for (Track sampleTrack : getSampleTracks()) {
            sampleAb.addTrack(sampleTrack);
        }
        return sampleAb;
    }

    /**
     * Populates the library with sample playlists
     * @param tracksOnlyLibrary library that only has tracks
     * @return
     */
    public static ReadOnlyLibrary populateSamplePlaylists(ReadOnlyLibrary tracksOnlyLibrary) {
        Library tracksOnlyLib = ((Library) tracksOnlyLibrary);
        for (Playlist samplePlaylist : getSamplePlaylists()) {
            tracksOnlyLib.addPlaylist(samplePlaylist);
        }
        return tracksOnlyLib;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static List<Track> getTrackList(String... strings) {
        return Arrays.stream(strings)
                .map(Name::new)
                .map(Track::new)
                .collect(Collectors.toList());
    }
}
