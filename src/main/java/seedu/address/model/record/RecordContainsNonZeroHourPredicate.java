package seedu.address.model.record;

import java.util.function.Predicate;

/**
 * Tests that a {@code Record}'s {@code hour} is more than 0.
 */
public class RecordContainsNonZeroHourPredicate implements Predicate<Record> {
    @Override
    public boolean test(Record record) {
        return Integer.parseInt(record.getHour().value) > 0;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsNonZeroHourPredicate); // instanceof handles nulls
    }
}
