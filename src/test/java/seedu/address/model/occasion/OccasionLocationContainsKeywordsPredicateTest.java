package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OccasionBuilder;

public class OccasionLocationContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("soc");
        List<String> secondPredicateKeywordList = Arrays.asList("soc", "utown");

        OccasionLocationContainsKeywordsPredicate firstPredicate =
                new OccasionLocationContainsKeywordsPredicate(firstPredicateKeywordList);
        OccasionLocationContainsKeywordsPredicate secondPredicate =
                new OccasionLocationContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OccasionLocationContainsKeywordsPredicate firstPredicateCopy =
                new OccasionLocationContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_occasionLocationContainsKeywords_returnsTrue() {
        // One keyword
        OccasionLocationContainsKeywordsPredicate predicate =
                new OccasionLocationContainsKeywordsPredicate(Collections.singletonList("soc"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionLocation("soc").build()));

        // Multiple keywords
        predicate = new OccasionLocationContainsKeywordsPredicate(Arrays.asList("soc", "utown"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionLocation("soc").build()));

        // Only one matching keyword
        predicate = new OccasionLocationContainsKeywordsPredicate(Arrays.asList("school", "computing"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionLocation("school of computing").build()));

        // Mixed-case keywords
        predicate = new OccasionLocationContainsKeywordsPredicate(Arrays.asList("SChoOl", "compUtiNG"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionLocation("school of computing").build()));
    }

    @Test
    public void test_occasionLocationDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        OccasionLocationContainsKeywordsPredicate predicate =
                new OccasionLocationContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OccasionBuilder().withOccasionLocation("soc").build()));

        // Non-matching keyword
        predicate = new OccasionLocationContainsKeywordsPredicate(Arrays.asList("sco"));
        assertFalse(predicate.test(new OccasionBuilder().withOccasionLocation("utown").build()));

        // Keywords match occasionName, occasionDate, but does not match occasionLocation
        predicate = new OccasionLocationContainsKeywordsPredicate(Arrays.asList("tutorial", "2018-01-01", "soc", "gg"));
        assertFalse(predicate.test(new OccasionBuilder().withOccasionName("tutorial").withOccasionDate("2018-01-01")
                .withOccasionLocation("utown").build()));
    }
}
