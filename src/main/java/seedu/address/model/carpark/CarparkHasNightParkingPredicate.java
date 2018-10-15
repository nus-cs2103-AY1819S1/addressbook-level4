package seedu.address.model.carpark;

import java.util.function.Predicate;

/**
 * Tests that a {@code Carpark} has night parking.
 */
public class CarparkHasNightParkingPredicate implements Predicate<Carpark> {

    @Override
    public boolean test(Carpark carpark) {
        return carpark.getNightParking().value.equals("YES");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
        //|| (other instanceof CarparkHasNightParkingPredicate // instanceof handles nulls
        //&& keyword.equals(((CarparkHasNightParkingPredicate) other).keyword)); // state check
    }

}

