package seedu.address.model.person;

import java.util.function.Predicate;

public class FeeFilterPredicate implements Predicate<Person> {
    private final double minLimit;

    public FeeFilterPredicate(double minLimit) {
        this.minLimit = minLimit;
    }


    @Override
    public boolean test(Person person) {
        return person.getFees().feesPerHr >= minLimit;

    }


}
