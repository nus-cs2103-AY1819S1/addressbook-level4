//@@author LZYAndy
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.PersonBuilder;

public class AddressContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AddressContainsKeywordsPredicate firstPredicate = new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        AddressContainsKeywordsPredicate secondPredicate = new AddressContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AddressContainsKeywordsPredicate firstPredicateCopy =
                new AddressContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testAddressContainsKeywordsReturnsTrue() {
        // One keyword
        AddressContainsKeywordsPredicate predicate =
                new AddressContainsKeywordsPredicate(Collections.singletonList("Jurong"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").build()));
    }

    @Test
    public void testAddressDoesNotContainKeywordsReturnsFalse() {
        AddressContainsKeywordsPredicate predicate = new AddressContainsKeywordsPredicate(Collections.emptyList());

        // Non-matching keyword
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345678"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match email and address, but does not match email
        predicate = new AddressContainsKeywordsPredicate(Arrays.asList("12345", "Main", "Street"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}
//@@author LZYAndy
