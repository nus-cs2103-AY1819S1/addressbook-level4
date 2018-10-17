package seedu.address.model.issue;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.IssueBuilder;

public class IssueContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        IssueContainsKeywordsPredicate firstPredicate = new IssueContainsKeywordsPredicate(firstPredicateKeywordList);
        IssueContainsKeywordsPredicate secondPredicate = new IssueContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        IssueContainsKeywordsPredicate firstPredicateCopy =
            new IssueContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different issue -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_issueStatementContainsKeywords_returnsTrue() {
        // One keyword
        IssueContainsKeywordsPredicate predicate =
            new IssueContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new IssueBuilder().withStatement("Alice Bob").build()));

        // Multiple keywords
        predicate = new IssueContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new IssueBuilder().withStatement("Alice Bob").build()));

        // Only one matching keyword
        predicate = new IssueContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new IssueBuilder().withStatement("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new IssueContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new IssueBuilder().withStatement("Alice Bob").build()));

        // Partial matching keywords
        predicate = new IssueContainsKeywordsPredicate(Arrays.asList("lice", "bO"));
        assertTrue(predicate.test(new IssueBuilder().withStatement("Alice Bob").build()));
    }

    @Test
    public void test_issueStatementDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        IssueContainsKeywordsPredicate predicate = new IssueContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new IssueBuilder().withStatement("Alice").build()));

        // Non-matching keyword
        predicate = new IssueContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new IssueBuilder().withStatement("Alice Bob").build()));
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // Keywords match description
        IssueContainsKeywordsPredicate predicate = new IssueContainsKeywordsPredicate(
                Arrays.asList("12345", "Main", "Street"));
        assertTrue(predicate.test(new IssueBuilder().withDescription("12345").build()));

        // Keywords partially matches description
        predicate = new IssueContainsKeywordsPredicate(Arrays.asList("null"));
        assertTrue(predicate.test(new IssueBuilder().withDescription("has NULL pointer").build()));
    }

    @Test
    public void test_descriptionAndIssueStatementDoNotContainKeywords_returnsFalse() {
        // Keywords do not match description or issue statement
        IssueContainsKeywordsPredicate predicate = new IssueContainsKeywordsPredicate(
                Arrays.asList("java", "c++", "exception"));
        assertFalse(predicate.test(new IssueBuilder().withStatement("ruby")
                .withDescription("null pointer").build()));
    }
}
