package seedu.address.model.carpark;

import java.util.function.Predicate;

/**
 * Tests that a {@code Carpark} is of a specified car park type.
 */
public class CarparkIsOfTypePredicate implements Predicate<Carpark> {

    private final String carparkType;


    public CarparkIsOfTypePredicate(String carparkType) {
        this.carparkType = carparkType;
    }

    @Override
    public boolean test(Carpark carpark) {

        switch (carparkType) {

            case "SURFACE":
                return carpark.getCarparkType().value.contains("SURFACE");

            case "MULTISTOREY":
                return carpark.getCarparkType().value.contains("MULTI-STOREY");

            case "BASEMENT":
                return carpark.getCarparkType().value.contains("BASEMENT");

            case "MECHANISED":
                return carpark.getCarparkType().value.contains("MECHANISED");

            case "COVERED":
                return carpark.getCarparkType().value.contains("COVERED");

            default:
                // should catch invalid input instead (to be implemented)
                return carpark.getCarparkType().value.contains("CAR PARK");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
        //|| (other instanceof CarparkHasNightParkingPredicate // instanceof handles nulls
        //&& keyword.equals(((CarparkHasNightParkingPredicate) other).keyword)); // state check
    }

}
