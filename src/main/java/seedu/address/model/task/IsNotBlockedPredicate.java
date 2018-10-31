package seedu.address.model.task;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.function.Predicate;

/**
 * Tests that a {@code Task} is not blocked. This means that it does not have a dependency.
 */
public class IsNotBlockedPredicate implements Predicate<Task> {

    @Override
    public boolean test(Task task) {
      return task
              .getDependency()
              .getDependencyCount()
              .equals(0);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IsNotBlockedPredicate); // instanceof handles nulls
    }

}
