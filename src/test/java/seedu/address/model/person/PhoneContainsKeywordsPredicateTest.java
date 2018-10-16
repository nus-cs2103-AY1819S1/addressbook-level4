package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("98765432");
        List<String> secondPredicateKeywordList = Arrays.asList("98765432", "98651111");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98765432"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "98765432"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // Only one matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "98761111"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));

        // contains invalid keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("98765432").build()));
    }

    @Test
    public void test_givenKeywordsDoesNotMatchAnyPhone_returnsFalse() {
        // Non-matching phone number
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("98765432"));
        assertFalse(predicate.test(new PersonBuilder().build()));

        // Multiple phone numbers
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("98765432", "98761111"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("98762222").build()));

        // keyword is not a phone number
        predicate = new PhoneContainsKeywordsPredicate(Collections.singletonList("Hello%%World"));
        assertFalse(predicate.test(new PersonBuilder().build()));
    }

}
