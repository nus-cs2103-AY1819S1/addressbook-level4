package seedu.souschef.model.recipe;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.souschef.testutil.IngredientsBuilder;
import seedu.souschef.testutil.InstructionBuilder;
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
    public void test_RecipeContainsKeywords_returnsTrue() {
        // One keyword matching name
        RecipeContainsKeywordsPredicate predicate =
                new RecipeContainsKeywordsPredicate(Collections.singletonList("Curry"));
        assertTrue(predicate.test(new RecipeBuilder().withName("curry").build()));

        // One keyword matching difficulty
        predicate = new RecipeContainsKeywordsPredicate(Collections.singletonList("3"));
        assertTrue(predicate.test(new RecipeBuilder().withDifficulty("3").build()));

        // One keyword matching cook time
        predicate = new RecipeContainsKeywordsPredicate(Collections.singletonList("44M"));
        assertTrue(predicate.test(new RecipeBuilder().withCooktime("44M").build()));

        // One keyword matching Ingredient
        predicate = new RecipeContainsKeywordsPredicate(Collections.singletonList("Oil"));
        assertTrue(predicate.test(new RecipeBuilder().withInstruction(
                new InstructionBuilder().withIngredients(
                        new IngredientsBuilder().addIngredient("oil", "ml", 5.0).build())
                        .build())
                .build()));

        // Multiple keywords
        predicate = new RecipeContainsKeywordsPredicate(Arrays.asList("pie", "5"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Apple Pie").withDifficulty("5").build()));

        // Mixed-case keywords
        predicate = new RecipeContainsKeywordsPredicate(Arrays.asList("OranGe", "CaKE"));
        assertTrue(predicate.test(new RecipeBuilder().withName("Orange Cake").build()));
    }

    @Test
    public void test_RecipeDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        RecipeContainsKeywordsPredicate predicate = new RecipeContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new RecipeBuilder().withName("Orange Cake").build()));

        // Non-matching keyword
        predicate = new RecipeContainsKeywordsPredicate(Arrays.asList("Carrot"));
        assertFalse(predicate.test(new RecipeBuilder().withName("Roti Prata").withDifficulty("2").withInstruction(
                new InstructionBuilder().withIngredients(
                        new IngredientsBuilder().addIngredient("oil", "ml", 5.0)
                        .addIngredient("floor", "g", 200).build())
                        .build())
                .build()));
    }
}
