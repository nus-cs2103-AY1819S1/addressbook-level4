package seedu.scheduler.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;

/**
 * Represents a list of duration object for reminder times
 */
public class ReminderDurationList {

    public static final String EMPTY_VALUE = "NONE";
    public static final String NO_REMINDER_DISPLAY = "No Reminder";
    public static final String REMINDER_DISPLAY_SUFFIX = " before the start time";
    private Set<Duration> values = new HashSet<>();


    /**
     * Default constructor for ReminderDurationList
     */
    public ReminderDurationList(){
    }

    /**
     * Constructs a {@code ReminderDurationList} with inputs
     *
     * @param reminderDurations a list of duration objects.
     */
    public ReminderDurationList(Collection<Duration> reminderDurations) {
        requireNonNull(reminderDurations);
        values = new HashSet<>(reminderDurations);

    }

    /**
     * Read a string stored in data file and convert to ReminderDurationList
     * @param durationList
     */
    public ReminderDurationList(String durationList) {
        if (!durationList.equals(ReminderDurationList.EMPTY_VALUE)) {
            List<String> durations = Arrays.asList(durationList.split(","));
            for (String durationValue: durations) {
                values.add(Duration.parse(durationValue.trim()));
            }
        }
    }

    public Set<Duration> get() {
        return values;
    }

    /**
     * Check if ReminderDurationList is empty
     * @return if ReminderDurationList is empty
     */
    public Boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     *
     * @return the current number of reminder times
     */
    public int size() {
        return values.size();
    }

    /**
     * @return true if the list contains only 1 single duration, used for postpone function now
     */
    public boolean isSingleValue() {
        return (values.size() == 1);
    }

    /**
     * add input duration object to the set
     * @param duration
     */
    public void add(Duration duration) {
        values.add(duration);
    }

    /**
     * add ReminderDurationList to the set
     * @param durationList
     */
    public void addAll(ReminderDurationList durationList) {
        values.addAll(durationList.get());
    }

    /**
     * remove input duration object from the set
     * @param duration
     * @return true if duration is not inside the set
     */
    public Boolean remove(Duration duration) {
        return values.remove(duration);
    }

    /**
     * postpone all reminders by durationToMinus
     * If the reminder becomes negative duration, set to 0
     * @param durationToMinus
     */
    public void postpone(Duration durationToMinus) {
        Set<Duration> newValues = new HashSet<>();
        for (Duration reminder: values) {
            Duration newReminder = reminder.minus(durationToMinus);
            if (newReminder.isNegative()) {
                newReminder = Duration.ZERO;
            }
            newValues.add(newReminder);
        }
        values = newValues;
    }

    /**
     * remove ReminderDurationList from the set
     * @param durationList
     * @return true if reminderDurationList removed is a subset
     */
    public Boolean removeAll(ReminderDurationList durationList) {
        return values.removeAll(durationList.get()); }

    /**
     * Get string input for the event
     * @return string input
     */
    public String getPrettyString() {
        if (values.isEmpty()) {
            return PREFIX_EVENT_REMINDER_DURATION.toString();
        }

        String output = "";
        for (Duration duration: values) {
            output += PREFIX_EVENT_REMINDER_DURATION + duration.toString().replace("PT", "");
        }
        return output;

    }

    /**
     * Get string display in event panel for the event
     * @return string input
     */
    public String getDisplayString() {
        if (values.isEmpty()) {
            return NO_REMINDER_DISPLAY;
        }
        String output = toString().replace("PT", "");
        output += REMINDER_DISPLAY_SUFFIX;
        return output;

    }

    @Override
    public String toString() {
        String output = "";
        if (values.size() == 0) {
            output = EMPTY_VALUE;
        } else {
            for (Duration duration: values) {
                output += duration.toString() + ", ";
            }
            output = output.substring(0, output.length() - 2);
        }
        return output;
    }

    @Override
    public boolean equals(Object other) {
        return ((this == other) || (
                other instanceof ReminderDurationList
                        && values.equals(((ReminderDurationList) other).get())));
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

}
