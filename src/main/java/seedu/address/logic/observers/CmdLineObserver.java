package seedu.address.logic.observers;

import java.util.Optional;

import seedu.address.commons.events.BaseEvent;


/** Represents an entity interested in observing the command line as the user types */
public interface CmdLineObserver {

    /* Each Observer will optionally return an Event that the Logic will post on its behalf */
    Optional<BaseEvent> observe(String currentInput);

}
