package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

import java.time.LocalDateTime;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToDateTimeEvent extends BaseEvent {

    public final LocalDateTime targetLocalDateTime;

    public JumpToDateTimeEvent(LocalDateTime targetLocalDateTime) {
        this.targetLocalDateTime = targetLocalDateTime;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}