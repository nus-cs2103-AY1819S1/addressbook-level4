package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.model.card.Card;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.model.card.IndexedQuestion;
import seedu.address.model.test.matchtest.MatchAttempt;
import seedu.address.model.test.matchtest.MatchTest;

/**
 * A utility class for matching test.
 */
public class MatchTestUtil {
    /**
     * Used to obtain the base0 indexes of matching question and answer in the test.
     * @param test The matchTest that is ongoing.
     * @param card The card that is you want to match.
     * @return the an array of indexes of size 2. With the first and second indexes representing the question and
     * answer respectively.
     */
    public static Index[] getIndexes(MatchTest test, Card card) {
        return new Index[] {
                Index.fromZeroBased(test.getQuestions().indexOf(card.getQuestion())),
                Index.fromZeroBased(test.getAnswers().indexOf(card.getAnswer()))
        };
    }

    /**
     * Will generate a correct MatchAttempt.
     */
    public static MatchAttempt generateCorrectMatchAttempt(Card cardToMatch, Index[] correctIndexes) {
        return new MatchAttempt(cardToMatch,
                new IndexedQuestion(cardToMatch.getQuestion(), correctIndexes[0].getOneBased()),
                new IndexedAnswer(cardToMatch.getAnswer(), correctIndexes[1].getOneBased()));
    }
}
