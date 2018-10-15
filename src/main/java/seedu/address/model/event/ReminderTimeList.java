package seedu.address.model.event;

import static java.lang.Boolean.FALSE;
import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a hashmap of <reminder time, hasPoppedUp> in the scheduler.
 * Guarantees: uniqueness
 */
public class ReminderTimeList {

    public Map<DateTime, Boolean> values = new HashMap<>();

    /**
     * Default constructor
     */
    public ReminderTimeList() {

    }

    /**
     * Constructs a {@code DateTime}.
     *
     * @param formattedDateTimes A local date time.
     */
    public ReminderTimeList(List<DateTime> formattedDateTimes) {
        requireNonNull(formattedDateTimes);
        for (DateTime formmattedDateTime: formattedDateTimes) {
            values.put(formmattedDateTime, FALSE);
        }
    }

    /**
     *
     * @return the current number of reminder times
     */
    public int size() {
        return values.size();
    }

    public Map<DateTime, Boolean> get () {
        return values;
    }


    @Override
    public String toString() {
        String output = "";
        for (DateTime reminderTime: values.keySet()) {
            output += reminderTime.toString() + ": " + values.get(reminderTime).toString() + ", " ;
        }
        if(values.size() > 0) {
            output = output.substring(0, output.length() - 2);
        }
        return output;
    }


    @Override
    public int hashCode() {
        return values.hashCode();
    }

}
