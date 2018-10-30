package seedu.thanepark.model.ride;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.thanepark.testutil.RideBuilder;

public class RideContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RideContainsKeywordsPredicate firstPredicate = new RideContainsKeywordsPredicate(firstPredicateKeywordList);
        RideContainsKeywordsPredicate secondPredicate = new RideContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RideContainsKeywordsPredicate firstPredicateCopy = new RideContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different ride -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        RideContainsKeywordsPredicate predicate = new RideContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new RideBuilder().withName("Alice Big").build()));

        // Multiple keywords
        predicate = new RideContainsKeywordsPredicate(Arrays.asList("Alice", "Big"));
        assertTrue(predicate.test(new RideBuilder().withName("Alice Big").build()));

        // Only one matching keyword
        predicate = new RideContainsKeywordsPredicate(Arrays.asList("Big", "Castle"));
        assertTrue(predicate.test(new RideBuilder().withName("Alice Castle").build()));

        // Mixed-case keywords
        predicate = new RideContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new RideBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RideContainsKeywordsPredicate predicate = new RideContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RideBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new RideContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new RideBuilder().withName("Alice Bob").build()));

        // Keywords match days since last maintenance, current waiting time and thanepark, but does not match name
        predicate = new RideContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertFalse(predicate.test(new RideBuilder().withName("Alice").withMaintenance("12345")
                .withWaitTime("1").withAddress("Main Street").build()));
    }
}
