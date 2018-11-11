package seedu.clinicio.model.patient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.clinicio.testutil.PatientBuilder;

public class PatientNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PatientNameContainsKeywordsPredicate firstPredicate =
                new PatientNameContainsKeywordsPredicate(firstPredicateKeywordList);
        PatientNameContainsKeywordsPredicate secondPredicate =
                new PatientNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PatientNameContainsKeywordsPredicate firstPredicateCopy =
                new PatientNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        PatientNameContainsKeywordsPredicate predicate = new PatientNameContainsKeywordsPredicate(Collections
                .singletonList("Alice"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new PatientNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new PatientNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new PatientNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PatientBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PatientNameContainsKeywordsPredicate predicate = new PatientNameContainsKeywordsPredicate(Collections
                .emptyList());
        assertFalse(predicate.test(new PatientBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new PatientNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and clinicio, but does not match name
        predicate = new PatientNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com",
                "Main", "Street"));
        assertFalse(predicate.test(new PatientBuilder().withName("Alice")
                .withPhone("12345").withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
