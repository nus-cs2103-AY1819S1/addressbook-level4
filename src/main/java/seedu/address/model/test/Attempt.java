package seedu.address.model.test;

import seedu.address.model.card.Card;
import seedu.address.model.card.Question;

/**
 * A base model for the different kinds of attempts that can be made in different tests.
 */
public abstract class Attempt {
    protected Card attemptedCard;
    protected String answer;

    public Attempt(Card attemptedCard, String answer) {
        this.attemptedCard = attemptedCard;
        this.answer = answer;
    }

    public abstract boolean isCorrect();

    public Question getQuestion() {
        return attemptedCard.getQuestion();
    }

    public String getRawAnswer() {
        return answer;
    }
}
