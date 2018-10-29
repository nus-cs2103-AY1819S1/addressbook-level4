package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.card.Card;

/**
 * Event class that is used to set up the necessary information for displaying card info.
 */
public class SetUpDisplayCardInfoEvent extends BaseEvent {

    public final Card selectedCard;

    public SetUpDisplayCardInfoEvent(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
