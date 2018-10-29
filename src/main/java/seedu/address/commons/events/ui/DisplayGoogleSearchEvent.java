package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.card.Card;

/**
 * An event to signal the info panel to change to the google search panel.
 */
public class DisplayGoogleSearchEvent extends BaseEvent {

    private final Card cardToGoogle;

    public DisplayGoogleSearchEvent(Card cardToGoogle) {
        this.cardToGoogle = cardToGoogle;
    }

    public Card getCardToGoogle() {
        return cardToGoogle;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
