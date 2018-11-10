package seedu.scheduler.model.event;

import static org.junit.Assert.assertEquals;
import static seedu.scheduler.testutil.TypicalEvents.DINNER_WITH_JOE_WEEKLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.DINNER_WITH_JOE_WEEK_ONE;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEARLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.JIM_BIRTHDAY_YEAR_ONE;
import static seedu.scheduler.testutil.TypicalEvents.LEAP_DAY_CELEBRATION_YEARLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.LEAP_DAY_CELEBRATION_YEAR_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STARTUP_LECTURE_MONTHLY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.STARTUP_LECTURE_MONTH_ONE;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAILY_LIST;
import static seedu.scheduler.testutil.TypicalEvents.STUDY_WITH_JANE_DAY_ONE;

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
        assertEquals(STUDY_WITH_JANE_DAILY_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(STUDY_WITH_JANE_DAY_ONE));

        // weekly event
        assertEquals(DINNER_WITH_JOE_WEEKLY_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(DINNER_WITH_JOE_WEEK_ONE));

        // monthly event
        assertEquals(STARTUP_LECTURE_MONTHLY_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(STARTUP_LECTURE_MONTH_ONE));

        // yearly event
        assertEquals(LEAP_DAY_CELEBRATION_YEARLY_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(LEAP_DAY_CELEBRATION_YEAR_ONE));
        assertEquals(JIM_BIRTHDAY_YEARLY_LIST,
                repeatEventGenerator.generateAllRepeatedEvents(JIM_BIRTHDAY_YEAR_ONE));
    }

    /**
     * Initialise RepeatEventGenerator if the instance is not yet created.
     */
    private void initialiseRepeatEventGenerator() {
        repeatEventGenerator = RepeatEventGenerator.getInstance();
    }
}
