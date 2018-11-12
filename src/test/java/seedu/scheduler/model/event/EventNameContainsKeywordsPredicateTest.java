package seedu.scheduler.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.scheduler.testutil.EventBuilder;

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

        // different event -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Play"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Play Work").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Play", "Work"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Play Work").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Play", "Work"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Play Study").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("plAy", "stUDy"));
        assertTrue(predicate.test(new EventBuilder().withEventName("Play Study").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withEventName("Play").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Study"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Play Work").build()));

        // Keywords match description and venue, but does not match event name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Again", "School"));
        assertFalse(predicate.test(new EventBuilder().withEventName("Study").withDescription("Again")
                .withVenue("School").build()));
    }
}
