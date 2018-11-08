package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.souschef.testutil.RecipeBuilder;

public class RecipeContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        RecipeContainsKeywordsPredicate firstPredicate =
                new RecipeContainsKeywordsPredicate(firstPredicateKeywordList);
        RecipeContainsKeywordsPredicate secondPredicate =
                new RecipeContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        RecipeContainsKeywordsPredicate firstPredicateCopy =
                new RecipeContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different recipe -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        RecipeContainsKeywordsPredicate predicate =
                new RecipeContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new RecipeContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));

        // Mixed-case keywords
        predicate = new RecipeContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RecipeContainsKeywordsPredicate predicate = new RecipeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new RecipeContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Alice Bob").build()));
    }
}
