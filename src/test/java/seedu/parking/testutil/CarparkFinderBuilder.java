package seedu.parking.testutil;

import seedu.parking.model.CarparkFinder;
import seedu.parking.model.carpark.Carpark;

/**
 * A utility class to help with building CarparkFinder objects.
 * Example usage: <br>
 *     {@code CarparkFinder cf = new CarparkFinderBuilder().withCarpark("Y16", "BLK 349-355 CLEMENTI AVE 2").build();}
 */
public class CarparkFinderBuilder {

    private CarparkFinder carparkFinder;

    public CarparkFinderBuilder() {
        carparkFinder = new CarparkFinder();
    }

    public CarparkFinderBuilder(CarparkFinder carparkFinder) {
        this.carparkFinder = carparkFinder;
    }

    /**
     * Adds a new {@code Carpark} to the {@code CarparkFinder} that we are building.
     */
    public CarparkFinderBuilder withCarpark(Carpark carpark) {
        carparkFinder.addCarpark(carpark);
        return this;
    }

    public CarparkFinder build() {
        return carparkFinder;
    }
}
