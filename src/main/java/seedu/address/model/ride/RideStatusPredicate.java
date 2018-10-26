package seedu.address.model.ride;

import java.util.function.Predicate;


public class RideStatusPredicate implements Predicate<Ride> {
    private final Status status;

    public RideStatusPredicate(Status status) {
        this.status = status;
    }

    @Override
    public boolean test(Ride ride) {
        return status.equals(ride.getStatus());
    }

}
