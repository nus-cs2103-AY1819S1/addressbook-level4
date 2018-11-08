package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateSamePredicateTest {
    private static final String SYSTEM_DATE = "130818";
    private static final String SYSTEM_DATE_CHECK = SYSTEM_DATE;

    @Test
    public void dateSamePredicate_equal_success() {

        assertEquals(SYSTEM_DATE, SYSTEM_DATE_CHECK);
        assertTrue(SYSTEM_DATE.equals(SYSTEM_DATE_CHECK));
        assertTrue(SYSTEM_DATE_CHECK.equals(SYSTEM_DATE));
    }
}
