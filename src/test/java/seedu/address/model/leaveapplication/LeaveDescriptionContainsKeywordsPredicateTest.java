package seedu.address.model.leaveapplication;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.LeaveApplicationWithEmployeeBuilder;

public class LeaveDescriptionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LeaveDescriptionContainsKeywordsPredicate firstPredicate =
                new LeaveDescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        LeaveDescriptionContainsKeywordsPredicate secondPredicate =
                new LeaveDescriptionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LeaveDescriptionContainsKeywordsPredicate firstPredicateCopy =
                new LeaveDescriptionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        LeaveDescriptionContainsKeywordsPredicate predicate =
                new LeaveDescriptionContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new LeaveApplicationWithEmployeeBuilder().withDescription("Alice Bob").build()));

        // Multiple keywords
        predicate = new LeaveDescriptionContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new LeaveApplicationWithEmployeeBuilder().withDescription("Alice Bob").build()));

        // Only one matching keyword
        predicate = new LeaveDescriptionContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new LeaveApplicationWithEmployeeBuilder().withDescription("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new LeaveDescriptionContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new LeaveApplicationWithEmployeeBuilder().withDescription("Alice Bob").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        LeaveDescriptionContainsKeywordsPredicate predicate =
                new LeaveDescriptionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new LeaveApplicationWithEmployeeBuilder().withDescription("Alice").build()));

        // Non-matching keyword
        predicate = new LeaveDescriptionContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new LeaveApplicationWithEmployeeBuilder().withDescription("Alice Bob").build()));
    }
}
