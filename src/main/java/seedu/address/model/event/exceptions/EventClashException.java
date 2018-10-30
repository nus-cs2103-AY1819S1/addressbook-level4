package seedu.address.model.event.exceptions;

/**
 * Signals that the operation will result in two clashing {@code Event}s (Events are considered clashing events if
 * they clash, as defined in {@code Event#isClashingEvent(Event)}).
 */
public class EventClashException extends RuntimeException {
    public EventClashException() {
        super("Operation would result in clash events");
    }
}
