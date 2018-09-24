package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.address.model.Scheduler;
import seedu.address.model.event.Event;
import seedu.address.model.event.Priority;
import seedu.address.model.event.RepeatType;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event CS2103_TUTORIAL = new EventBuilder().withUuid(UUID.randomUUID())
            .withEventName("CS2103 F11 Tutorial")
            .withStartDateTime(LocalDateTime.of(2018, 9, 21, 11, 0))
            .withEndDateTime(LocalDateTime.of(2018, 9, 21, 12, 0))
            .withDescription("F11 Tutorial").withPriority(Priority.MEDIUM).withVenue("Computing")
            .withRepeatType(RepeatType.NONE)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 9, 21, 12, 0))
            .build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code Scheduler} with all the typical persons.
     */
    public static Scheduler getTypicalScheduler() {
        Scheduler scheduler = new Scheduler();
        for (Event event : getTypicalEvents()) {
            scheduler.addEvent(event);
        }
        return scheduler;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(CS2103_TUTORIAL));
    }
}
