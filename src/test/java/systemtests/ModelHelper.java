package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.Playlist;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Playlist> PREDICATE_MATCHING_NO_PERSONS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Playlist> toDisplay) {
        Optional<Predicate<Playlist>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPlaylistList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Playlist... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Playlist> getPredicateMatching(Playlist other) {
        return playlist -> playlist.equals(other);
    }
}
