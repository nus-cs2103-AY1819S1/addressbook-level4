package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.card.Card;

/**
 * Retrieves a card and display the next question
 */
public class OpenEndedTestShowNextQuestionEvent extends BaseEvent {
    private Card card;

    public OpenEndedTestShowNextQuestionEvent(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Card getCard() {
        return card;
    }
}
