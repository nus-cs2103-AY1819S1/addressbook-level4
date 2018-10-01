package seedu.address.commons.core;

import seedu.address.commons.events.BaseEvent;

/**
 * Base class for *Manager classes
 *
 * Registers the class' event handlers in eventsCenter
 */
public class Anakin_ComponentManager {
    protected EventsCenter eventsCenter;

    /**
     * Uses default {@link EventsCenter}
     */
    public Anakin_ComponentManager() {
        this(EventsCenter.getInstance());
    }

    public Anakin_ComponentManager(EventsCenter eventsCenter) {
        this.eventsCenter = eventsCenter;
        eventsCenter.registerHandler(this);
    }

    protected void raise(BaseEvent event) {
        eventsCenter.post(event);
    }
}
