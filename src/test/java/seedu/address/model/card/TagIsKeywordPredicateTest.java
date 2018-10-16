package seedu.address.model.card;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.CardBuilder;

public class TagIsKeywordPredicateTest {
    @Test
    public void test_cardContainsTag_returnsTrue() {
        TopicIsKeywordPredicate predicate = new TopicIsKeywordPredicate("Physics");
        assertTrue(predicate.test(new CardBuilder().withTopics("Physics").build()));

        // Caps insensitive
        assertTrue(predicate.test(new CardBuilder().withTopics("pHySicS").build()));

        // Works for cards with multiple tags
        assertTrue(predicate.test(new CardBuilder().withTopics("GeneralKnowledge", "Physics").build()));
        assertTrue(predicate.test(new CardBuilder().withTopics("Physics", "GeneralKnowledge").build()));
        assertTrue(predicate.test(new CardBuilder().withTopics("GeneralKnowledge", "Physics", "Git").build()));
    }

    @Test
    public void test_cardDoesNotContainTag_returnsFalse() {
        TopicIsKeywordPredicate predicate = new TopicIsKeywordPredicate("NotATag");
        assertFalse(predicate.test(new CardBuilder().withTopics("ATag").build()));

        // Works for cards with multiple tags
        assertFalse(predicate.test(new CardBuilder().withTopics("ATag", "BTag").build()));
    }

    @Test
    public void equals() {
        String firstTagKeyword = "first";
        String secondTagKeyword = "second";

        TopicIsKeywordPredicate firstPredicate = new
                TopicIsKeywordPredicate(firstTagKeyword);
        TopicIsKeywordPredicate secondPredicate = new
                TopicIsKeywordPredicate(secondTagKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TopicIsKeywordPredicate firstPredicateCopy = new
                TopicIsKeywordPredicate(firstTagKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
