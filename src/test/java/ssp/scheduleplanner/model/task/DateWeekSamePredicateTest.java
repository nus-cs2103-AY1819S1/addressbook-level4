package ssp.scheduleplanner.model.task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class DateWeekSamePredicateTest {

    @Test
    public void dateWeekSamePredicate_equal_success() {
        List<String> dateList = new ArrayList<String>();
        List<String> dateListEqual = dateList;
        Calendar c = Calendar.getInstance();
        dateList.add(new SimpleDateFormat("ddMMyy").format(c.getTime()));

        assertEquals(dateList, dateListEqual);
        assertTrue(dateList.equals(dateListEqual));
        assertTrue(dateListEqual.equals(dateList));
    }
}
