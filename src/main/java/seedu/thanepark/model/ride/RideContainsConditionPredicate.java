package seedu.thanepark.model.ride;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Ride}'s attributes matches the predicate given.
 */
public class RideContainsConditionPredicate implements Predicate<Ride> {
    private final List<AttributePredicate> attributePredicates;

    public RideContainsConditionPredicate(List<AttributePredicate> predicates) {
        attributePredicates = predicates;
    }

    @Override
    public boolean test(Ride ride) {
        return attributePredicates.stream().allMatch(p -> {
            NumericAttribute attributeToTest = p.getAttribute();
            NumericAttribute rideAttributeToTest = ride.getAttribute(attributeToTest);
            return p.test(rideAttributeToTest);
        });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RideContainsConditionPredicate // instanceof handles nulls
                && attributePredicates.containsAll(((RideContainsConditionPredicate) other).attributePredicates));
    }
}
