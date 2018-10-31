package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

import java.time.LocalDateTime;

/**
 * Indicates a request for calendar ui to display a particular localDateTime
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
