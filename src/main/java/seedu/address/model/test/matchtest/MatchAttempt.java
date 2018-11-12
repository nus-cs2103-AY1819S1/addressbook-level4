package seedu.address.model.test.matchtest;

import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.IndexedAnswer;
import seedu.address.model.card.IndexedQuestion;
import seedu.address.model.test.Attempt;

/**
 * Represent an attempt to answer the question during a test.
 */
public class MatchAttempt extends Attempt {

    private IndexedAnswer indexedAnswer;

    private IndexedQuestion indexedQuestion;

    /**
     * @param cardWithQuestion Represents the card that is associated to the question.
     * @param answer Represents the Answer that is matched to the question.
     */
    public MatchAttempt(Card cardWithQuestion, IndexedQuestion question, IndexedAnswer answer) {
        super(cardWithQuestion, answer.value, checkCorrectness(cardWithQuestion, answer));
        indexedAnswer = answer;
        indexedQuestion = question;
    }

    public IndexedAnswer getIndexedAnswer() {
        return indexedAnswer;
    }

    public IndexedQuestion getIndexedQuestion() {
        return indexedQuestion;
    }

    private static boolean checkCorrectness(Card cardWithQuestion, Answer answer) {
        return cardWithQuestion.getAnswer().equals(answer);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof MatchAttempt)) {
            return false;
        }

        // state check
        MatchAttempt other = (MatchAttempt) obj;
        return attemptedCard.equals(other.attemptedCard);
    }
}
