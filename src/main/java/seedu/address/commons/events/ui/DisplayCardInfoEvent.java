package seedu.address.commons.events.ui;

import java.util.List;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.card.Card;
import seedu.address.model.test.Attempt;

/**
 * Represents a request to display the card info in the InfoPanel.
 */
public class DisplayCardInfoEvent extends BaseEvent {
    private final Card cardToDisplay;

    private final List<Attempt> attemptsByCard;

    public DisplayCardInfoEvent(Card cardToDisplay, List<Attempt> attemptsByCard) {
        this.attemptsByCard = attemptsByCard;
        this.cardToDisplay = cardToDisplay;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Card getCardToDisplay() {
        return cardToDisplay;
    }

    public List<Attempt> getAttemptsByCard() {
        return attemptsByCard;
    }
}
