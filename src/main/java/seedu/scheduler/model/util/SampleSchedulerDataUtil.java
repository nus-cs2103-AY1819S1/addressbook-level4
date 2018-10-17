package seedu.scheduler.model.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.scheduler.model.ReadOnlyScheduler;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.DateTime;
import seedu.scheduler.model.event.Description;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.event.Venue;
import seedu.scheduler.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Scheduler} with sample data.
 */
public class SampleSchedulerDataUtil {
    private static final ArrayList<UUID> CONSTANT_UID = new ArrayList<>(Arrays.asList(
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a621"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a622"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a623"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a624"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a625"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a626")));
    private static final ArrayList<UUID> CONSTANT_UUID = new ArrayList<>(Arrays.asList(
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a627"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a628"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a629"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a630"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a631"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a632")));

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(CONSTANT_UID.get(0),
                    CONSTANT_UUID.get(0),
                    new EventName("01 January 2018"),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 2, 0)),
                    new Description("01 January 2018"),
                    new Venue("Computing"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 1, 2, 0)),
                    getTagSet("play", "home"),
                    getReminderDurationList(0, 2)),
            new Event(CONSTANT_UID.get(1),
                    CONSTANT_UUID.get(1),
                    new EventName("02 January 2018"),
                    new DateTime(LocalDateTime.of(2018, 1, 2, 1, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 2, 2, 0)),
                    new Description("02 January 2018"),
                    new Venue("Science"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 2, 2, 0)),
                    getTagSet("play"),
                    getReminderDurationList(1)),
            new Event(CONSTANT_UID.get(2),
                    CONSTANT_UUID.get(2),
                    new EventName("03 January 2018"),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 1, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 2, 0)),
                    new Description("03 January 2018"),
                    new Venue("Arts"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 3, 2, 0)),
                    getTagSet("home"),
                    getReminderDurationList(0, 1, 3)),
            new Event(CONSTANT_UID.get(3),
                    CONSTANT_UUID.get(3),
                    new EventName("work"),
                    new DateTime(LocalDateTime.of(2018, 12, 12, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 12, 12, 1, 0)),
                    new Description("workAndWork"),
                    new Venue("Work"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 12, 12, 1, 0)),
                    getTagSet("work"),
                    getReminderDurationList()),
            new Event(CONSTANT_UID.get(4),
                    CONSTANT_UUID.get(4),
                    new EventName("Travel"),
                    new DateTime(LocalDateTime.of(2018, 6, 1, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 6, 1, 1, 0)),
                    new Description("travelOrTravel"),
                    new Venue("USA"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 6, 1, 1, 0)),
                    getTagSet("travel"),
                    getReminderDurationList(1)),
            new Event(CONSTANT_UID.get(5),
                    CONSTANT_UUID.get(5),
                    new EventName("Play"),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0)),
                    new Description("Play on first january"),
                    new Venue("Home"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 1, 1, 0)),
                    getTagSet("playMonth"),
                    getReminderDurationList(0, 1, 2, 3)),
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

    /**
     * Returns a reminderDurationList given index between 0 to 3
     */
    public static ReminderDurationList getReminderDurationList(Integer... index) {
        ReminderDurationList list = new ReminderDurationList();
        ArrayList<Duration> durations = new ArrayList<>(Arrays.asList(
                Duration.parse("PT15M"),
                Duration.parse("PT30M"),
                Duration.parse("PT1H30M"),
                Duration.parse("PT1H")));
        for(Integer i: index){
            list.add(durations.get(i));
        }
        return list;
    }

}
