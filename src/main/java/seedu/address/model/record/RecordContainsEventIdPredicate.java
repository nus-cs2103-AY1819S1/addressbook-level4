package seedu.address.model.record;

import java.util.function.Predicate;

import seedu.address.model.event.EventId;

/**
 * Tests that a {@code Record}'s {@code EventId} matches the given EventId.
 */
public class RecordContainsEventIdPredicate implements Predicate<Record> {
    private final EventId eventId;

    public RecordContainsEventIdPredicate(EventId eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean test(Record record) {
        return record.getEventId().id == eventId.id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RecordContainsEventIdPredicate // instanceof handles nulls
                && eventId == ((RecordContainsEventIdPredicate) other).eventId); // state check
    }

}
