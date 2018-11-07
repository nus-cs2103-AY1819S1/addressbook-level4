package seedu.scheduler.model.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.scheduler.logic.RepeatEventGenerator;
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
    private static final ArrayList<UUID> CONSTANT_EVENTUID = new ArrayList<>(Arrays.asList(
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a621"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a622"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a623"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a624"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a625"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a626"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a627"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a628")));
    private static final ArrayList<UUID> CONSTANT_EVENTSETUID = new ArrayList<>(Arrays.asList(
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a629"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a630"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a631"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a632"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a633"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a634"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a635"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a636")));

    public static Event[] getSampleEvents() {
        return new Event[] {
            new Event(CONSTANT_EVENTUID.get(0),
                    CONSTANT_EVENTSETUID.get(0),
                    new EventName("Discussion with Jack"),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 14, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 1, 17, 0)),
                    new Description("Talk about personal problems"),
                    new Venue("Jack's House"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 1, 17, 0)),
                    getTagSet("Talk", "Personal"),
                    getReminderDurationList(0, 2)),
            new Event(CONSTANT_EVENTUID.get(1),
                    CONSTANT_EVENTSETUID.get(1),
                    new EventName("Interview with John"),
                    new DateTime(LocalDateTime.of(2018, 1, 2, 13, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 2, 15, 0)),
                    new Description("Interview for position as a software engineer"),
                    new Venue("Jane Street"),
                    RepeatType.NONE,
                    new DateTime(LocalDateTime.of(2018, 1, 2, 15, 0)),
                    getTagSet("Interview", "Work"),
                    getReminderDurationList(1)),
            new Event(CONSTANT_EVENTUID.get(2),
                    CONSTANT_EVENTSETUID.get(2),
                    new EventName("Study with Jane"),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 10, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 11, 0)),
                    new Description("Study for MA2101"),
                    new Venue("NUS"),
                    RepeatType.DAILY,
                    new DateTime(LocalDateTime.of(2018, 1, 6, 11, 0)),
                    getTagSet("Study", "School"),
                    getReminderDurationList(0, 1, 3)),
            new Event(CONSTANT_EVENTUID.get(3),
                    CONSTANT_EVENTSETUID.get(3),
                    new EventName("Study with Jill"),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 11, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 3, 12, 0)),
                    new Description("Study for CS2103"),
                    new Venue("NUS"),
                    RepeatType.DAILY,
                    new DateTime(LocalDateTime.of(2018, 1, 6, 12, 0)),
                    getTagSet("Study", "School"),
                    getReminderDurationList()),
            new Event(CONSTANT_EVENTUID.get(4),
                    CONSTANT_EVENTSETUID.get(4),
                    new EventName("Dinner with Joe"),
                    new DateTime(LocalDateTime.of(2018, 3, 3, 3, 0)),
                    new DateTime(LocalDateTime.of(2018, 3, 3, 4, 0)),
                    new Description("Catch up and dinner with Joe"),
                    new Venue("Singapore"),
                    RepeatType.WEEKLY,
                    new DateTime(LocalDateTime.of(2018, 3, 17, 4, 0)),
                    getTagSet("Relaxation", "Dinner"),
                    getReminderDurationList()),
            new Event(CONSTANT_EVENTUID.get(5),
                    CONSTANT_EVENTSETUID.get(5),
                    new EventName("Jim's Birthday"),
                    new DateTime(LocalDateTime.of(2018, 12, 31, 0, 0)),
                    new DateTime(LocalDateTime.of(2018, 12, 31, 1, 0)),
                    new Description("Celebrate Jim's Birthday"),
                    new Venue("Jim's House"),
                    RepeatType.YEARLY,
                    new DateTime(LocalDateTime.of(2020, 12, 31, 1, 0)),
                    getTagSet("Celebration"),
                    getReminderDurationList(1, 2)),
            new Event(CONSTANT_EVENTUID.get(6),
                    CONSTANT_EVENTSETUID.get(6),
                    new EventName("Leap Day Celebration"),
                    new DateTime(LocalDateTime.of(2016, 2, 29, 0, 0)),
                    new DateTime(LocalDateTime.of(2016, 2, 29, 1, 0)),
                    new Description("Celebrate a day that happens one in 4 years"),
                    new Venue("Marina Bay Sands"),
                    RepeatType.YEARLY,
                    new DateTime(LocalDateTime.of(2020, 2, 29, 1, 0)),
                    getTagSet("Celebration"),
                    getReminderDurationList(0)),
            new Event(CONSTANT_EVENTUID.get(7),
                    CONSTANT_EVENTSETUID.get(7),
                    new EventName("Startup Lecture"),
                    new DateTime(LocalDateTime.of(2018, 1, 28, 8, 0)),
                    new DateTime(LocalDateTime.of(2018, 1, 28, 10, 0)),
                    new Description("Lecture about entrepreneurship"),
                    new Venue("iCube"),
                    RepeatType.MONTHLY,
                    new DateTime(LocalDateTime.of(2018, 3, 28, 10, 0)),
                    getTagSet("Timetable", "Study", "School"),
                    getReminderDurationList(0, 1, 2, 3)),
        };

    }

    public static ReadOnlyScheduler getSampleScheduler() {
        Scheduler sampleScheduler = new Scheduler();
        for (Event sampleEvent : getSampleEvents()) {
            sampleScheduler.addEvents(RepeatEventGenerator.getInstance().generateAllRepeatedEvents(sampleEvent));
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
        for (Integer i: index) {
            list.add(durations.get(i));
        }
        return list;
    }

}
