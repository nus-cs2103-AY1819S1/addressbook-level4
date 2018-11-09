package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DateWeekSamePredicateTest {

    @Test
    public void dateWeekSamePredicate_equal() {
        List<String> dateList = new ArrayList<String>();
        List<String> dateListEqual = dateList;
        List<String> dateListNotEqual = new ArrayList<>();
        dateList.add("130818");
        dateListNotEqual.add("130818");
        dateListNotEqual.add("140818");

        DateWeekSamePredicate dateWeekSamePredicate1 = new DateWeekSamePredicate(dateList);
        DateWeekSamePredicate dateWeekSamePredicate2 = new DateWeekSamePredicate(dateListNotEqual);

        //equal values -> return true
        assertEquals(dateList, dateListEqual);
        assertTrue(dateList.equals(dateListEqual));
        assertTrue(dateListEqual.equals(dateList));

        //same object -> returns true
        assertTrue(dateWeekSamePredicate1.equals(dateWeekSamePredicate1));

        //different value -> returns false
        assertFalse(dateWeekSamePredicate1.equals(null));
        assertFalse(dateWeekSamePredicate1.equals(dateWeekSamePredicate2));
        assertFalse(dateWeekSamePredicate2.equals(dateWeekSamePredicate1));


    }
}
