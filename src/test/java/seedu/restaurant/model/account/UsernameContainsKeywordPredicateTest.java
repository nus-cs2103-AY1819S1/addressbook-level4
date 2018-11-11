package seedu.restaurant.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class UsernameContainsKeywordPredicateTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        UsernameContainsKeywordPredicate firstPredicate = new UsernameContainsKeywordPredicate(
                firstPredicateKeyword);
        UsernameContainsKeywordPredicate secondPredicate = new UsernameContainsKeywordPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UsernameContainsKeywordPredicate firstPredicateCopy = new UsernameContainsKeywordPredicate(
                firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different item -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword only since account username cannot have spaces
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("root");
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Only one matching keyword
        predicate = new UsernameContainsKeywordPredicate("azhikai");
        assertTrue(predicate.test(new AccountBuilder().withUsername("azhikai").build()));

        // Mixed-case keywords
        predicate = new UsernameContainsKeywordPredicate("rOot");
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Two matches
        predicate = new UsernameContainsKeywordPredicate("zhikai");
        assertTrue(predicate.test(new AccountBuilder().withUsername("azhikai").build()));
        assertTrue(predicate.test(new AccountBuilder().withUsername("zhikai").build()));
    }

    @Test
    public void test_usernameDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("zhikai");
        assertFalse(predicate.test(new AccountBuilder().withUsername("root").build()));
    }

    @Test
    public void test_emptyKeyword_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("");
        predicate.test(new AccountBuilder().withUsername("root").build());
    }

    @Test
    public void test_multipleKeyword_throwsIllegalArgumentException() {
        thrown.expect(IllegalArgumentException.class);
        UsernameContainsKeywordPredicate predicate = new UsernameContainsKeywordPredicate("root root1 root3");
        predicate.test(new AccountBuilder().withUsername("root").build());
    }
}
