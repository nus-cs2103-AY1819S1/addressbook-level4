package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.UUID;

import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.Scheduler;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Priority;
import seedu.address.model.event.RepeatType;
import seedu.address.model.event.Venue;

/**
 * Contains utility methods for populating {@code Scheduler} with sample data.
 */
public class SampleSchedulerDataUtil {
    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(UUID.randomUUID(),
                    new EventName("CS2103 Tutorial"),
                    new DateTime(LocalDateTime.of(2018, 9, 21, 11, 0)),
                    new DateTime(LocalDateTime.of(2018, 9, 21, 12, 0)),
                    new Description("F11 Tutorial slot"),
                    Priority.LOW,
                    new Venue("School of Computing"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 9, 21, 11, 0)))
        };
    }

    public static ReadOnlyScheduler getSampleScheduler() {
        Scheduler sampleScheduler = new Scheduler();
        for (Event sampleEvent : getSampleEvents()) {
            sampleScheduler.addEvent(sampleEvent);
        }
        return sampleScheduler;
    }

}
