package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.restaurant.model.Model;
import seedu.restaurant.model.menu.Item;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Item> PREDICATE_MATCHING_NO_ITEMS = unused -> false;

    /**
     * Updates {@code Model}'s filtered list to display only {@code toDisplay}.
     */
    public static void setFilteredList(Model model, List<Item> toDisplay) {
        Optional<Predicate<Item>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredItemList(predicate.orElse(PREDICATE_MATCHING_NO_ITEMS));
    }

    /**
     * @see ModelHelper#setFilteredList(Model, List)
     */
    public static void setFilteredList(Model model, Item... toDisplay) {
        setFilteredList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Item} equals to {@code other}.
     */
    private static Predicate<Item> getPredicateMatching(Item other) {
        return item -> item.equals(other);
    }
}
