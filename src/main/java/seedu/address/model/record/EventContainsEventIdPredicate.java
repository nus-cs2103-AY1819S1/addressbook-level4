package seedu.address.model.record;

import java.util.function.Predicate;

import seedu.address.model.event.EventId;

/**
 * Tests that a {@code Record}'s {@code EventId} matches the given EventId.
 */
public class EventContainsEventIdPredicate implements Predicate<Record> {
    private final EventId eventId;

    public EventContainsEventIdPredicate(EventId eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean test(Record record) {
        return record.getEventId().id == eventId.id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsEventIdPredicate // instanceof handles nulls
                && eventId == ((EventContainsEventIdPredicate) other).eventId); // state check
    }

}
