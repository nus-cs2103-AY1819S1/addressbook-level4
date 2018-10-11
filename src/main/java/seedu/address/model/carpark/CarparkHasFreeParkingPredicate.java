package seedu.address.model.carpark;

import java.util.function.Predicate;

/**
 * To be added
 */
public class CarparkHasFreeParkingPredicate implements Predicate<Carpark> {
    private final String keyword;

    public CarparkHasFreeParkingPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Carpark carpark) {
        // To Be Implemented: Parsing of timing
        //String timePeriod = carpark.getNightParking().value;

        // Currently only filters out car parks without free-parking timings at all
        return !carpark.getFreeParking().value.equals("NO");
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
        //|| (other instanceof CarparkHasNightParkingPredicate // instanceof handles nulls
        //&& keyword.equals(((CarparkHasNightParkingPredicate) other).keyword)); // state check
    }

}


