package seedu.address.model.card;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.CardBuilder;

public class QuestionContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        QuestionContainsKeywordsPredicate firstPredicate = new
                QuestionContainsKeywordsPredicate(firstPredicateKeywordList);
        QuestionContainsKeywordsPredicate secondPredicate = new
                QuestionContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        QuestionContainsKeywordsPredicate firstPredicateCopy = new
                QuestionContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_questionContainsKeywords_returnsTrue() {
        // One keyword
        QuestionContainsKeywordsPredicate predicate = new
                QuestionContainsKeywordsPredicate(Collections.singletonList("Why"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Why is the earth round?").build()));

        // Multiple keywords
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Why", "earth"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Why is the earth round?").build()));

        // Only one matching keyword
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Why", "Git"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Why is the earth round?").build()));

        // Mixed-case keywords
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("wHy", "eArtH"));
        assertTrue(predicate.test(new CardBuilder().withQuestion("Why is the earth round?").build()));
    }

    @Test
    public void test_questionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        QuestionContainsKeywordsPredicate predicate = new QuestionContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CardBuilder().withQuestion("Why is the earth round?").build()));

        // Non-matching keyword
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("Git"));
        assertFalse(predicate.test(new CardBuilder().withQuestion("WHy is the earth round?").build()));

        // Keywords match answer, but does not match name
        predicate = new QuestionContainsKeywordsPredicate(Arrays.asList("gravity"));
        assertFalse(predicate.test(new CardBuilder().withQuestion("Why is the earth round?")
                .withAnswer("because of gravity").build()));
    }
}
