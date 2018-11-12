package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * predicate for fee filter
 */
public class FeeFilterPredicate implements Predicate<Person> {
    private final double minLimit;

    public FeeFilterPredicate(double minLimit) {
        this.minLimit = minLimit;
    }


    @Override
    public boolean test(Person person) {
        return Double.valueOf(person.getFees().value) >= minLimit;

    }


}
