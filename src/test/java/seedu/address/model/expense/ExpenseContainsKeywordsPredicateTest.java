package seedu.address.model.expense;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.testutil.ExpenseBuilder;

import javax.swing.*;

public class ExpenseContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        ArgumentMultimap firstPredicateKeywordMap = new ArgumentMultimap();
        firstPredicateKeywordMap.put(PREFIX_NAME, "first");
        ArgumentMultimap secondPredicateKeywordMap = new ArgumentMultimap();
        secondPredicateKeywordMap.put(PREFIX_NAME, "first second");

        ExpenseContainsKeywordsPredicate firstPredicate = new ExpenseContainsKeywordsPredicate(firstPredicateKeywordMap);
        ExpenseContainsKeywordsPredicate secondPredicate = new ExpenseContainsKeywordsPredicate(secondPredicateKeywordMap);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ExpenseContainsKeywordsPredicate firstPredicateCopy = new ExpenseContainsKeywordsPredicate(firstPredicateKeywordMap);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_expenseContainsKeywords_returnsTrue() {
        // One keyword
        ArgumentMultimap keywordsMap = ArgumentTokenizer.tokenize(" n/Alice", PREFIX_NAME);
        ExpenseContainsKeywordsPredicate predicate =
                new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));

        keywordsMap = ArgumentTokenizer.tokenize(" c/School", PREFIX_CATEGORY);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withCategory("School").build()));

        // Multiple keywords
        keywordsMap = ArgumentTokenizer.tokenize(" n/Alice Bob", PREFIX_NAME);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));

        // Only one matching name keyword
        keywordsMap = ArgumentTokenizer.tokenize(" n/Bob Carol", PREFIX_NAME);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        keywordsMap = ArgumentTokenizer.tokenize(" n/aLIce bOB", PREFIX_NAME);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ArgumentMultimap keywordsMap = ArgumentTokenizer.tokenize(" n/", PREFIX_NAME);
        ExpenseContainsKeywordsPredicate predicate =
                new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice").build()));

        // Non-matching keyword
        keywordsMap = ArgumentTokenizer.tokenize(" n/Carol", PREFIX_NAME);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice Bob").build()));

        // Keywords match category and address, but does not match name
        keywordsMap = ArgumentTokenizer.tokenize(" n/12345 2.00", PREFIX_NAME);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertFalse(predicate.test(new ExpenseBuilder().withName("Alice").withCategory("12345")
                .withCost("2.00").build()));
    }

    @Test
    public void test_tagContainKeywords_returnTrue() {
        // One tag keyword, one tag
        ArgumentMultimap keywordsMap = ArgumentTokenizer.tokenize(" t/Lunch", PREFIX_TAG);
        ExpenseContainsKeywordsPredicate predicate =
                new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withTags("Lunch").build()));

        // One tag keywords, multiple tags
        assertTrue(predicate.test(new ExpenseBuilder().withTags("Lunch", "Cheap").build()));

        // Multiple tag keywords, multiple tags, all match
        keywordsMap = ArgumentTokenizer.tokenize(" t/Lunch t/Cheap", PREFIX_TAG);
        predicate = new ExpenseContainsKeywordsPredicate(keywordsMap);
        assertTrue(predicate.test(new ExpenseBuilder().withTags("Cheap", "Lunch").build()));

        // Multiple tag keywords, multiple tags, one match
        assertTrue(predicate.test(new ExpenseBuilder().withTags("Nice", "Lunch").build()));

    }
}
