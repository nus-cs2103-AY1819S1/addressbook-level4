package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.timetable.Date;
import seedu.address.model.timetable.UniqueSchedule;
import seedu.address.model.user.User;

/**
 * A utility class containing a list of {@code UniqueSchedule} objects to be used in tests.
 */
public class TypicalUniqueSchedules {

    public static final List<User> LIST_USERS = TypicalUsers.getTypicalUsers();
    public static final UniqueSchedule SCHEDULE_1 = createUniqueSchedule(LIST_USERS.get(0));
    public static final UniqueSchedule SCHEDULE_2 = createUniqueSchedule(LIST_USERS.get(1));

    private TypicalUniqueSchedules() {} // prevents instantiation.

    /**
     * Returns a list of UniqueSchedules for testing purposes. The two schedules are randomly generated and linked to
     * typical users.
     * @return the list of unique schedules.
     */
    public static List<UniqueSchedule> getTypicalUniqueSchedules() {
        return new ArrayList<>(Arrays.asList(SCHEDULE_1, SCHEDULE_2));
    }

    /**
     * Creates a randomly generated unique schedule.
     * @return the unique schedule.
     */
    private static UniqueSchedule createUniqueSchedule(User user) {
        UniqueSchedule uniqueSchedule = new UniqueSchedule(user.getUsername());
        List<Date> dates = Date.generateRandomUniqueDates(2500);
        dates.stream().forEach(uniqueSchedule::add);

        return uniqueSchedule;
    }

}
