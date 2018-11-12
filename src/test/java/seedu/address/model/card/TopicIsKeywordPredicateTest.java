package seedu.address.model.card;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.testutil.CardBuilder;

public class TopicIsKeywordPredicateTest {
    @Test
    public void test_cardContainsTopic_returnsTrue() {
        TopicIsKeywordPredicate predicate = new TopicIsKeywordPredicate("Physics");
        assertTrue(predicate.test(new CardBuilder().withTopics("Physics").build()));

        // Works for cards with multiple Topic
        assertTrue(predicate.test(new CardBuilder().withTopics("GeneralKnowledge", "Physics").build()));
        assertTrue(predicate.test(new CardBuilder().withTopics("Physics", "GeneralKnowledge").build()));
        assertTrue(predicate.test(new CardBuilder().withTopics("GeneralKnowledge", "Physics", "Git").build()));
    }

    @Test
    public void test_cardDoesNotContainTopic_returnsFalse() {
        TopicIsKeywordPredicate predicate = new TopicIsKeywordPredicate("NotATopic");
        assertFalse(predicate.test(new CardBuilder().withTopics("ATopic").build()));

        // Caps insensitive
        assertFalse(predicate.test(new CardBuilder().withTopics("pHySicS").build()));

        // Works for cards with multiple topics
        assertFalse(predicate.test(new CardBuilder().withTopics("ATopic", "BTopic").build()));
    }

    @Test
    public void equals() {
        String firstTopicKeyword = "first";
        String secondTopicKeyword = "second";

        TopicIsKeywordPredicate firstPredicate = new
                TopicIsKeywordPredicate(firstTopicKeyword);
        TopicIsKeywordPredicate secondPredicate = new
                TopicIsKeywordPredicate(secondTopicKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TopicIsKeywordPredicate firstPredicateCopy = new
                TopicIsKeywordPredicate(firstTopicKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
