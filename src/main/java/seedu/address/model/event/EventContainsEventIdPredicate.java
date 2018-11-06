package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code EventId} matches the given EventId.
 */
public class EventContainsEventIdPredicate implements Predicate<Event> {
    private final EventId eventId;

    public EventContainsEventIdPredicate(EventId eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean test(Event event) {
        return event.getEventId().id == eventId.id;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsEventIdPredicate // instanceof handles nulls
                && eventId == ((EventContainsEventIdPredicate) other).eventId); // state check
    }
}
