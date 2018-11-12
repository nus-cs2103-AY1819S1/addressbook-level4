package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.card.Card;

/**
 * An event to signal the info panel to change to the google search panel.
 */
public class DisplayResearchPageEvent extends BaseEvent {

    private final Card cardToResearch;

    public DisplayResearchPageEvent(Card cardToResearch) {
        this.cardToResearch = cardToResearch;
    }

    public Card getCardToResearch() {
        return cardToResearch;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
