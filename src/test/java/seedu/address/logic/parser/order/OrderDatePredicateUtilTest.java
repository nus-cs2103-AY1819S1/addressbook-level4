package seedu.address.logic.parser.order;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.order.OrderDate;

public class OrderDatePredicateUtilTest {
    @Test
    public void test_invalidDateGiven_throwsParseException() throws ParseException {
        List<String> invalidDates = Collections.singletonList("first");
        assertParseFailure(invalidDates, OrderDate.MESSAGE_DATE_CONSTRAINTS);

        invalidDates = Collections.singletonList("57-10-1908 00:00:00");
        assertParseFailure(invalidDates, OrderDate.MESSAGE_DATE_CONSTRAINTS);

        invalidDates = Collections.singletonList("01-15-1908 00:00:00");
        assertParseFailure(invalidDates, OrderDate.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void test_unsortedList_returnSortedList() throws ParseException {
        List<String> stringDates = Arrays.asList("02-10-2018 10:00:00", "01-12-2018 12:00:00", "03-10-2018 10:00:00");
        List<Date> dates = new OrderDatePredicateUtil().parseDateKeywords(stringDates);

        assertTrue(is_dates_sorted(dates));
    }

    @Test
    public void test_moreThanTwoDatesSupplied_returnListSizeOfTwo() throws ParseException {
        List<String> stringDates = Arrays.asList("01-10-2018 10:00:00", "02-12-2018 12:00:00", "03-10-2018 10:00:00");
        List<Date> dates = new OrderDatePredicateUtil().parseDateKeywords(stringDates);

        assertTrue(dates.size() == 2);
    }

    /**
     * Util to check if the dates in the list are sorted
     */
    private boolean is_dates_sorted(List<Date> dates) {
        for (int i = 1; i < dates.size(); i++) {
            if (dates.get(i - 1).compareTo(dates.get(i)) > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Asserts that the parsing of {@code userInput} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    private void assertParseFailure(List<String> userInput, String expectedMessage) {
        try {
            List<Date> dates = new OrderDatePredicateUtil().parseDateKeywords(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }
}
