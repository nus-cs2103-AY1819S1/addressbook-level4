package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.test.TriviaResults;

/**
 * Indicates the TriviaResults has changed
 */
public class TriviaResultsChangedEvent extends BaseEvent {
    public final TriviaResults results;

    public TriviaResultsChangedEvent(TriviaResults results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "number of cards " + results.getResultList().size();
    }
}
