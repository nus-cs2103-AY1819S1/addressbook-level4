package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OccasionBuilder;

public class OccasionNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("project meeting");
        List<String> secondPredicateKeywordList = Arrays.asList("project meeting", "tutorial");

        OccasionNameContainsKeywordsPredicate firstPredicate =
                new OccasionNameContainsKeywordsPredicate(firstPredicateKeywordList);
        OccasionNameContainsKeywordsPredicate secondPredicate =
                new OccasionNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OccasionNameContainsKeywordsPredicate firstPredicateCopy =
                new OccasionNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_occasionNameContainsKeywords_returnsTrue() {
        // One keyword
        OccasionNameContainsKeywordsPredicate predicate =
                new OccasionNameContainsKeywordsPredicate(Collections.singletonList("project"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionName("project meeting").build()));

        // Multiple keywords
        predicate = new OccasionNameContainsKeywordsPredicate(Arrays.asList("project", "meeting"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionName("project meeting").build()));

        // Only one matching keyword
        predicate = new OccasionNameContainsKeywordsPredicate(Arrays.asList("project", "tutorial"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionName("project meeting").build()));

        // Mixed-case keywords
        predicate = new OccasionNameContainsKeywordsPredicate(Arrays.asList("pRojeCT", "mEEtiNG"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionName("project meeting").build()));
    }

    @Test
    public void test_occasionNameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        OccasionNameContainsKeywordsPredicate predicate =
                new OccasionNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OccasionBuilder().withOccasionName("project meeting").build()));

        // Non-matching keyword
        predicate = new OccasionNameContainsKeywordsPredicate(Arrays.asList("tutorial"));
        assertFalse(predicate.test(new OccasionBuilder().withOccasionName("project meeting").build()));

        // Keywords match occasionDate, occasionLocation, but does not match occasionname
        predicate = new OccasionNameContainsKeywordsPredicate(Arrays.asList("tutorial", "2018-01-01", "soc", "gg"));
        assertFalse(predicate.test(new OccasionBuilder().withOccasionName("meeting").withOccasionDate("2018-01-01")
                .withOccasionLocation("soc").build()));
    }
}
