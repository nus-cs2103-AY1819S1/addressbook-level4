package seedu.address.model.carpark;

import java.util.function.Predicate;

/**
 * To be added
 */
public class CarparkHasNightParkingPredicate implements Predicate<Carpark> {
    private final String keyword;

    public CarparkHasNightParkingPredicate(String keyword) {
        this.keyword = keyword;
    }

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

