package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class OverduePredicateTest {
    private static final int SYSTEM_DATE = 111118;
    private static final int SYSTEM_DATE_CHECK = SYSTEM_DATE;
    private static final int SYSTEM_DATE_NOT_SAME = 121118;

    @Test
    public void overduePredicateEqual() {

        OverduePredicate pred1 = new OverduePredicate(SYSTEM_DATE);
        OverduePredicate pred2 = new OverduePredicate(SYSTEM_DATE_NOT_SAME);

        //same values -> return true
        assertEquals(SYSTEM_DATE, SYSTEM_DATE_CHECK);
        assertTrue(SYSTEM_DATE == SYSTEM_DATE_CHECK);
        assertTrue(SYSTEM_DATE_CHECK == SYSTEM_DATE);

        //same object -> returns true
        assertTrue(pred1.equals(pred1));

        //same value but different object -> returns true
        assertTrue(pred1.equals(new OverduePredicate(SYSTEM_DATE)));

        //different value return false
        assertFalse(pred1.equals(null));
        assertFalse(pred1.equals(pred2));
    }
}
