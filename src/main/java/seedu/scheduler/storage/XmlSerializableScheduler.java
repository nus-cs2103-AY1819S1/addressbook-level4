package seedu.scheduler.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.scheduler.commons.exceptions.IllegalValueException;
import seedu.scheduler.model.ReadOnlyScheduler;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.Event;

/**
 * An Immutable Scheduler that is serializable to XML format
 */
@XmlRootElement(name = "scheduler")
public class XmlSerializableScheduler {

    @XmlElement
    private List<XmlAdaptedEvent> events;

    /**
     * Creates an empty XmlSerializableScheduler.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableScheduler() {
        events = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableScheduler(ReadOnlyScheduler src) {
        this();
        events.addAll(src.getEventList().stream().map(XmlAdaptedEvent::new).collect(Collectors.toList()));
    }

    /**
     * Converts this scheduler into the model's {@code Scheduler} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the
     * {@code XmlAdaptedEvent}.
     */
    public Scheduler toModelType() throws IllegalValueException {
        Scheduler scheduler = new Scheduler();
        for (XmlAdaptedEvent e : events) {
            Event event = e.toModelType();
            scheduler.addEvent(event);
        }
        return scheduler;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableScheduler)) {
            return false;
        }
        return events.equals(((XmlSerializableScheduler) other).events);
    }
}
