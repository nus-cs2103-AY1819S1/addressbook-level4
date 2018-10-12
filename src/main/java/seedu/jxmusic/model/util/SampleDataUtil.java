package seedu.jxmusic.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.jxmusic.model.Library;
import seedu.jxmusic.model.ReadOnlyLibrary;
import seedu.jxmusic.model.Track;
import seedu.jxmusic.model.Name;
import seedu.jxmusic.model.Playlist;

/**
 * Contains utility methods for populating {@code Library} with sample data.
 */
public class SampleDataUtil {
    public static Playlist[] getSamplePlaylists() {
        return new Playlist[] {
            new Playlist(new Name("Aldnoah Zero"), getTrackList("aliez", "acyort")),
            new Playlist(new Name("Favourites"), getTrackList("scarborough fair", "ihojin no yaiba"))
        };
    }

    public static ReadOnlyLibrary getSampleLibrary() {
        Library sampleAb = new Library();
        for (Playlist samplePlaylist : getSamplePlaylists()) {
            sampleAb.addPlaylist(samplePlaylist);
        }
        return sampleAb;
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
