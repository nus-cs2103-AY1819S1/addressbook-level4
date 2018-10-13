package seedu.address.model.ride;

import java.util.function.Predicate;

/**
 * Tests that a {@code Ride}'s attributes matches the predicate given.
 */
public class RideContainsConditionPredicate implements Predicate<Ride> {
    private final WaitTimePredicate waitTimePredicate;

    public RideContainsConditionPredicate(WaitTimePredicate predicate) {
        waitTimePredicate = predicate;
    }

    @Override
    public boolean test(Ride ride) {
        return waitTimePredicate.test(ride.getWaitingTime());
    }
}
