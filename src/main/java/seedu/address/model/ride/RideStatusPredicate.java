package seedu.address.model.ride;

import java.util.function.Predicate;

/**
 * Tests that a {@code Ride}'s attributes matches the status given.
 */
public class RideStatusPredicate implements Predicate<Ride> {
    private final Status status;

    public RideStatusPredicate(Status status) {
        this.status = status;
    }

    @Override
    public boolean test(Ride ride) {
        return status.equals(ride.getStatus());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RideStatusPredicate // instanceof handles nulls
                && (status.equals(((RideStatusPredicate) other).status))); // state check
    }

}
