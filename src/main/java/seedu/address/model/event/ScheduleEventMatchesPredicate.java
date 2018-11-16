package seedu.address.model.event;

import java.util.function.Predicate;


/**
 * Tests that a {@code ScheduleEvent}'s {@code Date} satisfies the constraints given.
 */
public class ScheduleEventMatchesPredicate implements Predicate<ScheduleEvent> {

    private final String constraints;

    /**
     * Change the argument to something which is used to match with this event.
     * It's currently set to String
     */
    public ScheduleEventMatchesPredicate(String constraints) {
        this.constraints = constraints;
    }

    @Override
    public boolean test(ScheduleEvent event) {
        return event.getId().toString().equals(constraints);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleEventMatchesPredicate // instance of handles nulls
                && constraints.equals(((ScheduleEventMatchesPredicate) other).constraints)); // state check
    }
}
