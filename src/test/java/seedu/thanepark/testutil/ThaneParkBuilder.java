package seedu.thanepark.testutil;

import seedu.thanepark.model.ThanePark;
import seedu.thanepark.model.ride.Ride;

/**
 * A utility class to help with building ThanePark objects.
 * Example usage: <br>
 *     {@code ThanePark ab = new ThaneParkBuilder().withRide("John", "Doe").build();}
 */
public class ThaneParkBuilder {

    private ThanePark thanePark;

    public ThaneParkBuilder() {
        thanePark = new ThanePark();
    }

    public ThaneParkBuilder(ThanePark thanePark) {
        this.thanePark = thanePark;
    }

    /**
     * Adds a new {@code Ride} to the {@code ThanePark} that we are building.
     */
    public ThaneParkBuilder withRide(Ride ride) {
        thanePark.addRide(ride);
        return this;
    }

    public ThanePark build() {
        return thanePark;
    }
}
