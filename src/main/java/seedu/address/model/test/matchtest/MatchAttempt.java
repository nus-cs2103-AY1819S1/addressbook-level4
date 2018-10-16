package seedu.address.model.test.matchtest;

import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;

/**
 * Represent an attempt to answer the question during a test.
 */
public class MatchAttempt extends Attempt {
    private final Card cardWithAnswer;

    /**
     * @param cardWithQuestion Represents the card that is associated to the question.
     * @param cardWithAnswer Represents the card that is associated to the answer.
     */
    public MatchAttempt(Card cardWithQuestion, Card cardWithAnswer) {
        super(cardWithQuestion, cardWithAnswer.getAnswer().toString());
        this.cardWithAnswer = cardWithAnswer;
    }

    public Answer getAnswer() {
        return cardWithAnswer.getAnswer();
    }

    @Override
    public boolean isCorrect() {
        return attemptedCard.equals(cardWithAnswer);
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
        return attemptedCard.equals(other.attemptedCard)
            && cardWithAnswer.equals(other.cardWithAnswer);
    }
}
