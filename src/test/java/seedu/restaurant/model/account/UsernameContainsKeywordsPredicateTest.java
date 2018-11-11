package seedu.restaurant.model.account;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.restaurant.testutil.account.AccountBuilder;

//@@author AZhiKai
public class UsernameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        UsernameContainsKeywordsPredicate firstPredicate = new UsernameContainsKeywordsPredicate(
                firstPredicateKeywordList);
        UsernameContainsKeywordsPredicate secondPredicate = new UsernameContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        UsernameContainsKeywordsPredicate firstPredicateCopy = new UsernameContainsKeywordsPredicate(
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
        UsernameContainsKeywordsPredicate predicate = new UsernameContainsKeywordsPredicate(Collections.singletonList(
                "root"));
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Only one matching keyword
        predicate = new UsernameContainsKeywordsPredicate(Arrays.asList("root", "azhikai"));
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Mixed-case keywords
        predicate = new UsernameContainsKeywordsPredicate(Arrays.asList("rOot"));
        assertTrue(predicate.test(new AccountBuilder().withUsername("root").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        UsernameContainsKeywordsPredicate predicate = new UsernameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new AccountBuilder().withUsername("root").build()));

        // Non-matching keyword
        predicate = new UsernameContainsKeywordsPredicate(Arrays.asList("azhikai"));
        assertFalse(predicate.test(new AccountBuilder().withUsername("root").build()));
    }
}
