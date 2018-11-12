package seedu.address.testutil;

import seedu.address.model.timetable.Date;

/**
 * A utility class containing a list of {@code Date} objects to be used in tests.
 */
public class TypicalDates {

    public static final Date DATE_A = new DateBuilder().build();
    public static final Date DATE_B = new DateBuilder().withWeek("15").build();
    public static final Date DATE_C = new DateBuilder().withTime("0600").build();
    public static final Date DATE_READING = new DateBuilder().withDay("Tue").withTime("1330")
            .withWeek("reading").build();
    public static final Date DATE_AFTER_READING = new DateBuilder().withDay("Tue").withTime("1330")
            .withWeek("14").build();
    public static final Date DATE_RECESS = new DateBuilder().withWeek("recess").withDay("Sun").withTime("1500").build();
    public static final Date DATE_AFTER_RECESS = new DateBuilder().withWeek("7")
            .withDay("Sun").withTime("1500").build();

}
