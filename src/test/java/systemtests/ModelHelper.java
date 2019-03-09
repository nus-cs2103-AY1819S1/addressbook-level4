package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;
import seedu.jxmusic.model.Track;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {

    private static final Predicate<Playlist> PREDICATE_MATCHING_NO_PLAYLISTS = unused -> false;
    private static final Predicate<Track> PREDICATE_MATCHING_NO_TRACKS = unused -> false;


    /**
     * Updates {@code model}'s filtered track list to display only {@code toDisplay}.
     */
    public static void setFilteredTrackList(Model model, List<Track> toDisplay) {
        Optional<Predicate<Track>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);

        model.updateFilteredTrackList(predicate.orElse(PREDICATE_MATCHING_NO_TRACKS));
    }

    /**
     * @see ModelHelper#setFilteredTrackList(Model, List)
     */
    public static void setFilteredTrackList(Model model, Track... toDisplay) {
        setFilteredTrackList(model, Arrays.asList(toDisplay));
    }

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Playlist> toDisplay) {
        Optional<Predicate<Playlist>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);

        model.updateFilteredPlaylistList(predicate.orElse(PREDICATE_MATCHING_NO_PLAYLISTS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Playlist... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Playlist} equals to {@code other}.
     */
    private static Predicate<Playlist> getPredicateMatching(Playlist other) {
        return playlist -> playlist.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Track} equals to {@code other}.
     */
    private static Predicate<Track> getPredicateMatching(Track other) {
        return track -> track.equals(other);
    }
}

