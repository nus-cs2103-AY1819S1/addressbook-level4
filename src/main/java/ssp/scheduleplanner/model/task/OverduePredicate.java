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
