package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyScheduler;
import seedu.address.model.Scheduler;
import seedu.address.model.event.DateTime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.RepeatType;
import seedu.address.model.event.Venue;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Scheduler} with sample data.
 */
public class SampleSchedulerDataUtil {

    private static final UUID CONSTANT_UUID = UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a625");

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(CONSTANT_UUID,
                    new EventName("01 January 2018"),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 2, 0)),
                    new Description("01 January 2018"),
                    new Venue("Computing"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 1, 2, 0)),
                    getTagSet("play", "home")),
            new Event(CONSTANT_UUID,
                    new EventName("02 January 2018"),
                    new DateTime(LocalDateTime.of(2018, 1, 2, 1, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 2, 2, 0)),
                    new Description("02 January 2018"),
                    new Venue("Science"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 2, 2, 0)),
                    getTagSet("play")),
            new Event(CONSTANT_UUID,
                    new EventName("03 January 2018"),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 1, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 2, 0)),
                    new Description("03 January 2018"),
                    new Venue("Arts"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 3, 2, 0)),
                    getTagSet("home")),
            new Event(CONSTANT_UUID,
                    new EventName("work"),
                    new DateTime(LocalDateTime.of(2018, 12, 12, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 12, 12, 1, 0)),
                    new Description("workAndWork"),
                    new Venue("Work"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 12, 12, 1, 0)),
                    getTagSet("work")),
            new Event(CONSTANT_UUID,
                    new EventName("Travel"),
                    new DateTime(LocalDateTime.of(2018, 6, 1, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 6, 1, 1, 0)),
                    new Description("travelOrTravel"),
                    new Venue("USA"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 6, 1, 1, 0)),
                    getTagSet("travel")),
            new Event(CONSTANT_UUID,
                    new EventName("Play"),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0)),
                    new Description("Play on first january"),
                    new Venue("Home"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0)),
                    getTagSet("playMonth"))
        };

    }

    public static ReadOnlyScheduler getSampleScheduler() {
        Scheduler sampleScheduler = new Scheduler();
        for (Event sampleEvent : getSampleEvents()) {
            sampleScheduler.addEvent(sampleEvent);
        }
        return sampleScheduler;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
