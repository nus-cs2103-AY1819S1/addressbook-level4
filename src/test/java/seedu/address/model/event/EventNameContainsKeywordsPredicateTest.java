package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ScheduledEventBuilder;

public class EventNameContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventNameContainsKeywords_returnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.singletonList(
                "Meeting"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventName("Meeting").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Meeting", "Appointment"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventName("Meeting Appointment").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Meeting", "Class"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventName("Meeting").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("MEeTing", "ApPOinTmeNt"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventName("Meeting Appointment").build()));
    }

    @Test
    public void test_eventNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ScheduledEventBuilder().withEventName("Meeting").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Class"));
        assertFalse(predicate.test(new ScheduledEventBuilder().withEventName("Meeting Appointment").build()));

        // Keywords match event description, address but not name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("SOCLecture", "testDesc"));
        assertFalse(predicate.test(new ScheduledEventBuilder()
                .withEventName("nonmatching name")
                .withEventDescription("testDesc")
                .withEventAddress("SOCLecture")
                .build()));
    }
}
