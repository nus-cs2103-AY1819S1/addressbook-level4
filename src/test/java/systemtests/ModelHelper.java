package systemtests;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.module.Module;
import seedu.address.model.occasion.Occasion;

/**
 * Contains helper methods to set up {@code Model} for testing.
 */
public class ModelHelper {
    private static final Predicate<Person> PREDICATE_MATCHING_NO_PERSONS = unused -> false;
    private static final Predicate<Module> PREDICATE_MATCHING_NO_MODULES = unused -> false;
    private static final Predicate<Occasion> PREDICATE_MATCHING_NO_OCCASIONS = unused -> false;

    /**
     * Updates {@code model}'s filtered personlist to display only {@code toDisplay}.
     */
    public static void setFilteredPersonList(Model model, List<Person> toDisplay) {
        Optional<Predicate<Person>> predicate =
                toDisplay.stream().map(ModelHelper::getPredicateMatching).reduce(Predicate::or);
        model.updateFilteredPersonList(predicate.orElse(PREDICATE_MATCHING_NO_PERSONS));
    }

    /**
     * Updates {@code model}'s filtered module list to display only {@code toDisplay}.
     */
    public static void setFilteredModuleList(Model model, List<Module> toDisplay) {
        Optional<Predicate<Module>> predicate =
                toDisplay.stream().map(ModelHelper::getModulePredicateMatching).reduce(Predicate::or);
        model.updateFilteredModuleList(predicate.orElse(PREDICATE_MATCHING_NO_MODULES));
    }

    /**
     * Updates {@code model}'s filtered occasion list to display only {@code toDisplay}.
     */
    public static void setFilteredOccasionList(Model model, List<Occasion> toDisplay) {
        Optional<Predicate<Occasion>> predicate =
                toDisplay.stream().map(ModelHelper::getOccasionPredicateMatching).reduce(Predicate::or);
        model.updateFilteredOccasionList(predicate.orElse(PREDICATE_MATCHING_NO_OCCASIONS));
    }

    /**
     * @see ModelHelper#setFilteredPersonList(Model, List)
     */
    public static void setFilteredPersonList(Model model, Person... toDisplay) {
        setFilteredPersonList(model, Arrays.asList(toDisplay));
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Person} equals to {@code other}.
     */
    private static Predicate<Person> getPredicateMatching(Person other) {
        return person -> person.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Module} equals to {@code other}.
     */
    private static Predicate<Module> getModulePredicateMatching(Module other) {
        return module -> module.equals(other);
    }

    /**
     * Returns a predicate that evaluates to true if this {@code Occasion} equals to {@code other}.
     */
    private static Predicate<Occasion> getOccasionPredicateMatching(Occasion other) {
        return occasion -> occasion.equals(other);
    }

}
