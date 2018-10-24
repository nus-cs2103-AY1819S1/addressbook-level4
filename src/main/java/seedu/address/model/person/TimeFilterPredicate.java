package seedu.address.model.person;

import java.util.ArrayList;
import java.util.function.Predicate;

/**
 * TimeFilterPredicate
 */

public class TimeFilterPredicate implements Predicate<Person> {
    private Time time;
    public TimeFilterPredicate(Time time) {
        this.time = time;
    }

    @Override
    public boolean test(Person person) {
        ArrayList<Time> timeList = person.getTime();
        return timeList.contains(time);
    }
}


