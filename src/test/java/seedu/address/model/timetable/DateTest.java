package seedu.address.model.timetable;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static seedu.address.testutil.TypicalDates.DATE_A;
import static seedu.address.testutil.TypicalDates.DATE_AFTER_READING;
import static seedu.address.testutil.TypicalDates.DATE_AFTER_RECESS;
import static seedu.address.testutil.TypicalDates.DATE_B;
import static seedu.address.testutil.TypicalDates.DATE_READING;
import static seedu.address.testutil.TypicalDates.DATE_RECESS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void compareTo() {
        // same values, return 0.
        assertTrue(DATE_A.compareTo(DATE_A) == 0);

        // testing recess week and reading week.
        assertTrue(DATE_READING.compareTo(DATE_AFTER_READING) == -1);
        assertTrue(DATE_RECESS.compareTo(DATE_AFTER_RECESS) == -1);
    }

    @Test
    public void equals() {
        // same object -> returns true.
        assertTrue(DATE_A.equals(DATE_A));

        // different object, same values -> returns true.
        assertTrue(DATE_A.equals(new Date(DATE_A.getWeek(), DATE_A.getDay(), DATE_A.getTime())));

        // different values -> returns false.
        assertFalse(DATE_A.equals(DATE_AFTER_READING));

        // different week -> returns false
        assertFalse(DATE_A.equals(DATE_B));
    }
}
