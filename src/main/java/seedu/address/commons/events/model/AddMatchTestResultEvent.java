package seedu.address.commons.events.model;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.test.matchtest.MatchTest;

/**
 * Trigger an event to add the matching test result to the model.
 */
public class AddMatchTestResultEvent extends BaseEvent {
    private MatchTest matchTest;

    public AddMatchTestResultEvent(MatchTest matchTest) {
        this.matchTest = matchTest;
    }

    public MatchTest getMatchTest() {
        return matchTest;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
