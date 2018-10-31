package seedu.parking.model.carpark;

import java.util.function.Predicate;

/**
 * Tests that a {@code Carpark}'s {@code carparkNumber} matches any of the keywords given.
 */
public class CarparkIsOfNumberPredicate implements Predicate<Carpark> {
    private final String carparkNumber;

    public CarparkIsOfNumberPredicate(String carparkNumber) {
        this.carparkNumber = carparkNumber;
    }

    @Override
    public boolean test(Carpark carpark) {
        return carpark.getCarparkNumber().value.equals(carparkNumber);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CarparkIsOfNumberPredicate // instanceof handles nulls
                && carparkNumber.equals(((CarparkIsOfNumberPredicate) other).carparkNumber)); // state check
    }
}
