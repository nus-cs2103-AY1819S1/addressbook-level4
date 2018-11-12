package ssp.scheduleplanner.model.task;

import java.util.function.Predicate;

/**
 * This encapsulates the information regarding the OverduePredicate class.
 */
public class OverduePredicate implements Predicate<Task> {
    private final int date;

    public OverduePredicate(int date) {
        this.date = date;
    }

    /**
     * A function to see if the date of the task is before or after the current date.
     * @param task The task to test.
     * @return true if the task's date is after the current date, false if the task's date is before.
     */
    @Override
    public boolean test(Task task) {
        return date - task.getDate().yymmdd > 0 ? true : false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof OverduePredicate
                && date == ((OverduePredicate) other).date);
    }

}
