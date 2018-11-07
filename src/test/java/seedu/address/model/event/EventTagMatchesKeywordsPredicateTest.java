package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.ScheduledEventBuilder;

public class EventTagMatchesKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventTagMatchesKeywordsPredicate firstPredicate =
                new EventTagMatchesKeywordsPredicate(firstPredicateKeywordList);
        EventTagMatchesKeywordsPredicate secondPredicate =
                new EventTagMatchesKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventTagMatchesKeywordsPredicate firstPredicateCopy =
                new EventTagMatchesKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventTagMatchesKeywords_returnsTrue() {
        // One keyword
        EventTagMatchesKeywordsPredicate predicate = new EventTagMatchesKeywordsPredicate(Collections.singletonList(
                "Meeting"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventTags("Meeting").build()));

        // Multiple keywords
        predicate = new EventTagMatchesKeywordsPredicate(Arrays.asList("Meeting", "Appointment"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventTags("Meeting", "Appointment").build()));

        // Only one matching keyword
        predicate = new EventTagMatchesKeywordsPredicate(Arrays.asList("Meeting", "Class"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventTags("Meeting").build()));

        // Mixed-case keywords
        predicate = new EventTagMatchesKeywordsPredicate(Arrays.asList("MEeTing", "ApPOinTmeNt"));
        assertTrue(predicate.test(new ScheduledEventBuilder().withEventTags("Meeting", "Appointment").build()));
    }

    @Test
    public void test_eventTagDoesNotMatchKeywords_returnsFalse() {
        // Zero keywords
        EventTagMatchesKeywordsPredicate predicate = new EventTagMatchesKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ScheduledEventBuilder().withEventTags("Meeting").build()));

        // Non-matching keyword
        predicate = new EventTagMatchesKeywordsPredicate(Arrays.asList("Class"));
        assertFalse(predicate.test(new ScheduledEventBuilder().withEventTags("Meeting", "Appointment").build()));

        // Keywords match event name, description but not tag
        predicate = new EventTagMatchesKeywordsPredicate(Arrays.asList("SOCLecture", "testDesc"));
        assertFalse(predicate.test(new ScheduledEventBuilder()
                .withEventName("SOCLecture")
                .withEventDescription(
                "testDesc")
                .withEventTags("Class")
                .build()));
    }
}
