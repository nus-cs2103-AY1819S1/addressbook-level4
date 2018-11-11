package seedu.address.model.calendarevent;

import org.junit.Test;
import seedu.address.testutil.CalendarEventBuilder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DatePredicateTest {

    @Test
    public void equals() {
        DateTime Date1 = new DateTime("2018-11-11 08:00");
        DateTime Date2 = new DateTime("2018-11-12 09:00");
        DateTime Date3 = new DateTime("2018-11-13 10:00");
        DateTime Date4 = new DateTime("2018-11-14 11:00");

        DatePredicate firstPredicate = new DatePredicate(Date1, Date2);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DatePredicate firstPredicateCopy = new DatePredicate(Date1, Date2);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different dates -> return false
        DatePredicate secondPredicate = new DatePredicate(Date3, Date4);
        assertFalse(firstPredicate.equals(secondPredicate));

        // same dates, different order -> returns false
        DatePredicate thirdPredicate = new DatePredicate(Date2, Date1);
        assertFalse(firstPredicate.equals(thirdPredicate));

        DatePredicate fourthPredicate = new DatePredicate(Date1, null);
        DatePredicate fifthPredicate = new DatePredicate(null, Date2);

        // 1 null value, other value same -> returns true
        DatePredicate fourthPredicateCopy = new DatePredicate(Date1, null);
        assertTrue(fourthPredicate.equals(fourthPredicateCopy));

        DatePredicate fifthPredicateCopy = new DatePredicate(null, Date2);
        assertTrue(fifthPredicate.equals(fifthPredicateCopy));

        // 1 null value vs 2 values -> returns false
        assertFalse(firstPredicate.equals(fourthPredicate));
        assertFalse(firstPredicate.equals(fifthPredicate));

        // both 2 null values -> returns true
        DatePredicate nullPredicate1 = new DatePredicate(null, null);
        DatePredicate nullPredicate2 = new DatePredicate(null, null);
        assertTrue(nullPredicate1.equals(nullPredicate2));

        // 2 null values vs 1 null value -> returns false
        assertFalse(fourthPredicate.equals(nullPredicate1));
        assertFalse(fifthPredicate.equals(nullPredicate1));

        // 2 null values vs 2 values -> returns false
        assertFalse(firstPredicate.equals(nullPredicate1));
    }

    @Test
    public void test_dateIsWithinRange_returnsTrue() {
        String DateString1 = "2018-11-11 08:00";
        String DateString2 = "2018-11-12 08:00";
        String DateString3 = "2018-11-13 08:00";
        String DateString4 = "2018-11-14 08:00";
        String DateString5 = "2018-11-15 08:00";
        String DateString6 = "2018-11-16 08:00";

        DateTime Date2 = new DateTime(DateString2);
        DateTime Date5 = new DateTime(DateString5);

        // predicate with both 'from' and 'to' date/time values (not null)
        // note: "within range" refers to the interval after the 'from' date/time and before the 'to' date/time
        DatePredicate predicate = new DatePredicate(Date2, Date5);

        // event start & end both within range  -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString3).withEnd(DateString4).build()));

        // event start on 'from' date/time, end within range -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString2).withEnd(DateString4).build()));

        // event start before 'from' date/time and end within range -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString4).build()));

        // event start before 'from' date/time, end on 'from' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString2).build()));

        // event start within range, end on 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString3).withEnd(DateString5).build()));

        // event start within range, end after 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString3).withEnd(DateString6).build()));

        // event start on 'to' date/time && end after 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString5).withEnd(DateString6).build()));


        // predicate with only 'from' date/time value ('to' value null)
        predicate = new DatePredicate(Date2, null);

        // event start & end after 'from' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString3).withEnd(DateString4).build()));

        // event start before 'from' date/time, end after 'from' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString4).build()));

        // event start on 'from' date/time, end after 'from' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString2).withEnd(DateString4).build()));

        // event start before 'from' date/time, end on 'from' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString2).build()));


        // predicate with only 'to' date/time value ('from' value null)
        predicate = new DatePredicate(null, Date5);

        // event start & end before 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString3).withEnd(DateString4).build()));

        // event start before 'to' date/time, end after 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString6).build()));

        // event start before 'to' date/time, end on 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString5).build()));

        // event start on 'to' date/time && end after 'to' date/time -> returns true
        assertTrue(predicate.test(new CalendarEventBuilder().withStart(DateString5).withEnd(DateString6).build()));


        // predicate with both 'to' and 'from' null -> always returns true
        DatePredicate nullPredicate = new DatePredicate(null, null);
        assertTrue(nullPredicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString2).build()));
    }

    @Test
    public void test_dateIsNotWithinRange_returnsFalse() {
        String DateString1 = "2018-11-11 08:00";
        String DateString2 = "2018-11-12 08:00";
        String DateString3 = "2018-11-13 08:00";
        String DateString4 = "2018-11-14 08:00";
        String DateString5 = "2018-11-15 08:00";
        String DateString6 = "2018-11-16 08:00";

        DateTime Date3 = new DateTime(DateString3);
        DateTime Date4 = new DateTime(DateString4);

        // predicate with 'from' and 'to' values (not null)
        DatePredicate predicate = new DatePredicate(Date3, Date4);

        // event start & end both before 'from' date/time -> returns false
        assertFalse(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString2).build()));

        // event start & end both after 'to' date/time -> returns false
        assertFalse(predicate.test(new CalendarEventBuilder().withStart(DateString5).withEnd(DateString6).build()));


        //predicate with only 'from' value ('to' value null)
        predicate = new DatePredicate(Date3, null);

        // event start & end before 'from' date/time -> returns false
        assertFalse(predicate.test(new CalendarEventBuilder().withStart(DateString1).withEnd(DateString2).build()));


        // predicate with only 'to' value ('from' value null)
        predicate = new DatePredicate(null, Date4);

        // event start & end after 'to' date/time -> returns false
        assertFalse(predicate.test(new CalendarEventBuilder().withStart(DateString5).withEnd(DateString6).build()));
    }
}
