package seedu.address.model.order;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OrderBuilder;

public class OrderDatePredicateTest {
    @Test
    public void equals() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date dateOne = null;
        Date dateTwo = null;

        try {
            dateOne = sf.parse("01-10-2018 10:00:00");
            dateTwo = sf.parse("03-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        List<Date> firstPredicateKeywordList = Collections.singletonList(dateOne);
        List<Date> secondPredicateKeywordList = Arrays.asList(dateOne, dateTwo);

        OrderDatePredicate firstPredicate =
                new OrderDatePredicate(firstPredicateKeywordList);
        OrderDatePredicate secondPredicate =
                new OrderDatePredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OrderDatePredicate firstPredicateCopy =
                new OrderDatePredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_singleDateGivenMatchDate_returnsTrue() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date datePredicateKeyword = null;

        try {
            datePredicateKeyword = sf.parse("01-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        List<Date> predicateKeywordList = Collections.singletonList(datePredicateKeyword);

        // Exact match
        OrderDatePredicate predicate = new OrderDatePredicate(predicateKeywordList);
        assertTrue(predicate.test(new OrderBuilder().withDate("01-10-2018 10:00:00").build()));
    }

    @Test
    public void test_singleDateGivenDateDoesNotMatch_returnsFalse() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date datePredicateKeyword = null;

        try {
            datePredicateKeyword = sf.parse("01-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        List<Date> predicateKeywordList = Collections.singletonList(datePredicateKeyword);

        // Different date, same time
        OrderDatePredicate predicate = new OrderDatePredicate(predicateKeywordList);
        assertFalse(predicate.test(new OrderBuilder().withDate("03-10-2018 10:00:00").build()));

        // Same date, different time
        assertFalse(predicate.test(new OrderBuilder().withDate("01-10-2018 13:00:00").build()));
    }

    @Test
    public void test_twoDatesGivenDateWithinBoundary_returnsTrue() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date lowerDateBoundary = null;
        Date upperDateBoundary = null;

        try {
            lowerDateBoundary = sf.parse("01-10-2018 10:00:00");
            upperDateBoundary = sf.parse("03-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        List<Date> predicateKeywordList = Arrays.asList(lowerDateBoundary, upperDateBoundary);

        // Equals lower boundary
        OrderDatePredicate predicate = new OrderDatePredicate(predicateKeywordList);
        assertTrue(predicate.test(new OrderBuilder().withDate("01-10-2018 10:00:00").build()));

        // Equals upper boundary
        assertTrue(predicate.test(new OrderBuilder().withDate("03-10-2018 10:00:00").build()));

        // Within boundary
        assertTrue(predicate.test(new OrderBuilder().withDate("02-10-2018 10:00:00").build()));
    }

    @Test
    public void test_twoDatesGivenDateOutsideBoundary_returnsFalse() {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        Date lowerDateBoundary = null;
        Date upperDateBoundary = null;

        try {
            lowerDateBoundary = sf.parse("01-10-2018 10:00:00");
            upperDateBoundary = sf.parse("03-10-2018 10:00:00");
        } catch (ParseException pE) {
            assertFalse(true);
        }

        List<Date> predicateKeywordList = Arrays.asList(lowerDateBoundary, upperDateBoundary);

        // Below lower boundary
        OrderDatePredicate predicate = new OrderDatePredicate(predicateKeywordList);
        assertFalse(predicate.test(new OrderBuilder().withDate("01-10-2018 09:00:59").build()));

        // Exceed upper boundary
        assertFalse(predicate.test(new OrderBuilder().withDate("03-10-2018 10:00:01").build()));
    }
}
