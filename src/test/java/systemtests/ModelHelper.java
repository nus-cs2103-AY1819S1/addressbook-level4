package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.parking.model.Model;
import seedu.parking.model.carpark.Carpark;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Carpark> PREDICATE_MATCHING_NO_CARPARKS = unused -> false;

    /**
     * Updates {@code model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Carpark> toDisplay) {
        Optional<Predicate<Carpark>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredCarparkList(predicate.orElse(PREDICATE_MATCHING_NO_CARPARKS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Carpark... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Carpark} equals to {@code other}.
     */
    private static Predicate<Carpark> getPredicateMatching(Carpark other) {
        return carpark -> carpark.equals(other);
    }
}
