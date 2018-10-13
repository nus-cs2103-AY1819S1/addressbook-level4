package seedu.address.model.ride;

import java.util.function.Predicate;

/**
 * Test that waiting time matches the conditions given
 */
public class WaitTimePredicate extends WaitTime implements Predicate<WaitTime> {
    private char operator;

    public WaitTimePredicate(char operator, String value) {
        super(value);
        this.operator = operator;
    }

    @Override
    public boolean test(WaitTime waitTime) {
        switch (operator) {

        case '<':
            return waitTime.getValue() < this.getValue();
        case '>':
            return waitTime.getValue() > this.getValue();
        default:
            return false;
        }
    }
}
