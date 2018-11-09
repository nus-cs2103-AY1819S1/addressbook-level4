package seedu.restaurant.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class AccountContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        AccountContainsKeywordsPredicate firstPredicate = new AccountContainsKeywordsPredicate(
                firstPredicateKeywordList);
        AccountContainsKeywordsPredicate secondPredicate = new AccountContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        AccountContainsKeywordsPredicate firstPredicateCopy = new AccountContainsKeywordsPredicate(
                firstPredicateKeywordList);
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
        AccountContainsKeywordsPredicate predicate = new AccountContainsKeywordsPredicate(Collections.singletonList(
                "root"));
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Only one matching keyword
        predicate = new AccountContainsKeywordsPredicate(Arrays.asList("root", "azhikai"));
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Mixed-case keywords
        predicate = new AccountContainsKeywordsPredicate(Arrays.asList("rOot"));
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        AccountContainsKeywordsPredicate predicate = new AccountContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Non-matching keyword
        predicate = new AccountContainsKeywordsPredicate(Arrays.asList("azhikai"));
        assertFalse(predicate.test(new AccountBuilder().withUsername("root").build()));
    }
}
