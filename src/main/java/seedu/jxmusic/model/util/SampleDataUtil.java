package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.model.Library;
import seedu.address.model.ReadOnlyLibrary;
import seedu.address.model.Track;
import seedu.address.model.Name;
import seedu.address.model.Playlist;

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
