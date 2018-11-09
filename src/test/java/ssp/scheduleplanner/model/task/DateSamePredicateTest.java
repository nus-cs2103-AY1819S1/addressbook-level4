package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateSamePredicateTest {
    private static final String SYSTEM_DATE = "130818";
    private static final String SYSTEM_DATE_CHECK = SYSTEM_DATE;
    private static final String SYSTEM_DATE_NOT_SAME = "140818";

    @Test
    public void dateSamePredicate_equal() {

        DateSamePredicate dateSamePredicate1 = new DateSamePredicate(SYSTEM_DATE);
        DateSamePredicate dateSamePredicate2 = new DateSamePredicate(SYSTEM_DATE_NOT_SAME);

        //same values -> return true
        assertEquals(SYSTEM_DATE, SYSTEM_DATE_CHECK);
        assertTrue(SYSTEM_DATE.equals(SYSTEM_DATE_CHECK));
        assertTrue(SYSTEM_DATE_CHECK.equals(SYSTEM_DATE));

        //same object -> returns true
        assertTrue(dateSamePredicate1.equals(dateSamePredicate1));

        //same value but different object -> returns true
        assertTrue(dateSamePredicate1.equals(new DateSamePredicate(SYSTEM_DATE)));

        //different value return false
        assertFalse(dateSamePredicate1.equals(null));
        assertFalse(dateSamePredicate1.equals(dateSamePredicate2));
    }
}
