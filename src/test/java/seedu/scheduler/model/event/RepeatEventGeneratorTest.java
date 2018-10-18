package seedu.scheduler.model.event;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.testutil.TypicalEvents.DAY2018_THREE_DAY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.FEBRUARY_29_2016_YEARLY;
import static seedu.scheduler.testutil.TypicalEvents.FEBRUARY_29_FOUR_YEAR_LIST;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_1_2018_MONTHLY;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_1_2018_YEARLY;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_1_THREE_YEAR_LIST;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_30_2018_DAILY;
import static seedu.scheduler.testutil.TypicalEvents.JANUARY_31_2018_MONTHLY;
import static seedu.scheduler.testutil.TypicalEvents.MONTH2018_1_THREE_MONTH_LIST;
import static seedu.scheduler.testutil.TypicalEvents.MONTH2018_31_THREE_MONTH_LIST;

import org.junit.Before;
import org.junit.Test;

import seedu.scheduler.logic.RepeatEventGenerator;

public class RepeatEventGeneratorTest {
    private RepeatEventGenerator repeatEventGenerator;

    @Before
    public void setUp() {
        initialiseRepeatEventGenerator();
    }

    @Test
    public void generateAllRepeatedEvents() {
        // daily event
        assertEquals(DAY2018_THREE_DAY_LIST, repeatEventGenerator.generateAllRepeatedEvents(JANUARY_30_2018_DAILY));

        // monthly event
        assertEquals(MONTH2018_1_THREE_MONTH_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(JANUARY_1_2018_MONTHLY));
        assertEquals(MONTH2018_31_THREE_MONTH_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(JANUARY_31_2018_MONTHLY));

        // yearly event
        assertEquals(JANUARY_1_THREE_YEAR_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(JANUARY_1_2018_YEARLY));
        assertEquals(FEBRUARY_29_FOUR_YEAR_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(FEBRUARY_29_2016_YEARLY));
    }

    @Test
    public void generateDailyRepeatEvents() {
        // standard day of a year
        assertEquals(DAY2018_THREE_DAY_LIST, repeatEventGenerator.generateDailyRepeatEvents(JANUARY_30_2018_DAILY));
    }

    @Test
    public void generateMonthlyRepeatEvents() {
        // standard day of a year
        assertEquals(MONTH2018_1_THREE_MONTH_LIST,
                repeatEventGenerator.generateMonthlyRepeatEvents(JANUARY_1_2018_MONTHLY));
        // days that do not appear every month
        assertEquals(MONTH2018_31_THREE_MONTH_LIST,
                repeatEventGenerator.generateMonthlyRepeatEvents(JANUARY_31_2018_MONTHLY));
    }

    @Test
    public void generateYearlyRepeatEvents() {
        // standard day of year
        assertEquals(JANUARY_1_THREE_YEAR_LIST,
                repeatEventGenerator.generateYearlyRepeatEvents(JANUARY_1_2018_YEARLY));
        // day of leap year
        assertEquals(FEBRUARY_29_FOUR_YEAR_LIST,
                repeatEventGenerator.generateYearlyRepeatEvents(FEBRUARY_29_2016_YEARLY));
    }

    /**
     * Initialise RepeatEventGenerator if the instance is not yet created.
     */
    private void initialiseRepeatEventGenerator() {
        repeatEventGenerator = RepeatEventGenerator.getInstance();
    }
}
