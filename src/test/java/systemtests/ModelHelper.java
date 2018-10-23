package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Volunteer> PREDICATE_MATCHING_NO_VOLUNTEERS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Volunteer> toDisplay) {
        Optional<Predicate<Volunteer>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredVolunteerList(predicate.orElse(PREDICATE_MATCHING_NO_VOLUNTEERS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Volunteer... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Volunteer} equals to {@code other}.
     */
    private static Predicate<Volunteer> getPredicateMatching(Volunteer other) {
        return volunteer -> volunteer.equals(other);
    }
}
