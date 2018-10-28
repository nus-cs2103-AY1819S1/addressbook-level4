package seedu.address.model.occasion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.OccasionBuilder;

public class OccasionDateContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("2018-01-01");
        List<String> secondPredicateKeywordList = Arrays.asList("2018-01-01", "2018-01-02");

        OccasionDateContainsKeywordsPredicate firstPredicate =
                new OccasionDateContainsKeywordsPredicate(firstPredicateKeywordList);
        OccasionDateContainsKeywordsPredicate secondPredicate =
                new OccasionDateContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        OccasionDateContainsKeywordsPredicate firstPredicateCopy =
                new OccasionDateContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_occasionDateContainsKeywords_returnsTrue() {
        // One keyword
        OccasionDateContainsKeywordsPredicate predicate =
                new OccasionDateContainsKeywordsPredicate(Collections.singletonList("2018-01-01"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionDate("2018-01-01").build()));

        // Only one matching keyword
        predicate = new OccasionDateContainsKeywordsPredicate(Arrays.asList("2018-01-01", "2018-01-02"));
        assertTrue(predicate.test(new OccasionBuilder().withOccasionDate("2018-01-01").build()));

    }

    @Test
    public void test_occasionDateDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        OccasionDateContainsKeywordsPredicate predicate =
                new OccasionDateContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new OccasionBuilder().withOccasionDate("2018-01-01").build()));

        // Non-matching keyword
        predicate = new OccasionDateContainsKeywordsPredicate(Arrays.asList("2018-01-01"));
        assertFalse(predicate.test(new OccasionBuilder().withOccasionDate("2018-01-02").build()));

        // Keywords match occasionName, occasionLocation, but does not match occasionDate
        predicate = new OccasionDateContainsKeywordsPredicate(Arrays.asList("tutorial", "2018-01-01", "soc", "gg"));
        assertFalse(predicate.test(new OccasionBuilder().withOccasionName("tutorial").withOccasionDate("2018-01-02")
                .withOccasionLocation("soc").build()));
    }
}
