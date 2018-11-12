
package seedu.scheduler.testutil;

import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_CS2103;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA3220;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.RepeatType;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final String KEYWORD_MATCHING_STARTUP = "Startup"; // A keyword that matches Study
    public static final ArrayList<UUID> CONSTANT_EVENTUID = new ArrayList<>(Arrays.asList(
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a621"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a622"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a623"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a624"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a625"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a626"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a627"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a628"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a629"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a630"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a631"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a632"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a645"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a646"), //13
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a647"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a648"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a649"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a650"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a651"), //18
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a652"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a653"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a654"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a655"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a656"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a657"))); //24
    public static final ArrayList<UUID> CONSTANT_EVENTSETUID = new ArrayList<>(Arrays.asList(
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a633"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a634"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a635"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a636"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a637"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a638"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a639"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a640"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a641"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a642"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a643"),
            UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a644")));

    // single event
    public static final Event DISCUSSION_WITH_JACK = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(0))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(0))
            .withEventName("Discussion with Jack")
            .withStartDateTime(LocalDateTime.of(2018, 1, 1, 14, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 1, 17, 0))
            .withDescription("Talk about personal problems").withVenue("Jack's House")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 1, 17, 0))
            .withTags("Talk", "Personal")
            .withReminderDurationList(getReminderDurationList(0, 2)).build();
    public static final Event INTERVIEW_WITH_JOHN = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(1))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(1))
            .withEventName("Interview with John")
            .withStartDateTime(LocalDateTime.of(2018, 1, 2, 13, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 2, 15, 0))
            .withDescription("Interview for position as a software engineer").withVenue("Jane Street")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 2, 15, 0))
            .withTags("Interview", "Work")
            .withReminderDurationList(getReminderDurationList(1)).build();
    public static final Event CHRISTMAS = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("Christmas")
            .withStartDateTime(LocalDateTime.of(2018, 12, 25, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 25, 23, 0))
            .withDescription("Celebration")
            .withVenue("Home")
            .withRepeatType(RepeatType.NONE)
            .withReminderDurationList(getReminderDurationList(1))
            .build();
    public static final Event AFTER_CHRISTMAS = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("After Christmas")
            .withStartDateTime(LocalDateTime.of(2018, 12, 26, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 26, 23, 0))
            .withDescription("Celebration")
            .withVenue("Home")
            .withRepeatType(RepeatType.NONE)
            .withReminderDurationList(getReminderDurationList(1))
            .build();
    public static final Event LOCAL_SINGLE_EVENT = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .build();
    public static final Event LOCAL_REPEAT_EVENT = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withRepeatType(RepeatType.DAILY)
            .build();
    public static final Event CHRISTMASEVE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("Christmas Eve")
            .withStartDateTime(LocalDateTime.of(2018, 12, 24, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 24, 23, 0))
            .withDescription("Celebration")
            .withVenue("Home")
            .withRepeatType(RepeatType.NONE)
            .build();
    public static final Event EXAM_CS2103 = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS2103 Examination")
            .withStartDateTime(LocalDateTime.of(2018, 12, 5, 17, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 5, 19, 0))
            .withDescription("MCQ+Essay")
            .withVenue("MPSH")
            .withRepeatType(RepeatType.NONE)
            .build();
    public static final Event EXAM_CS2103_UPDATED = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS2103 Exam")
            .withStartDateTime(LocalDateTime.of(2018, 12, 5, 17, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 5, 19, 0))
            .withDescription("MCQ+Essay")
            .withVenue("MPSH2")
            .withRepeatType(RepeatType.NONE)
            .build();

    // daily event
    public static final Event STUDY_WITH_JANE_DAY_ONE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(2))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(2))
            .withEventName("Study with Jane")
            .withStartDateTime(LocalDateTime.of(2018, 1, 3, 10, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 3, 11, 0))
            .withDescription("Study for MA2101").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 11, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 3))
            .build();
    public static final Event STUDY_WITH_JANE_DAY_TWO = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(3))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(2))
            .withEventName("Study with Jane")
            .withStartDateTime(LocalDateTime.of(2018, 1, 4, 10, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 4, 11, 0))
            .withDescription("Study for MA2101").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 11, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 3))
            .build();
    public static final Event STUDY_WITH_JANE_DAY_THREE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(4))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(2))
            .withEventName("Study with Jane")
            .withStartDateTime(LocalDateTime.of(2018, 1, 5, 10, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 5, 11, 0))
            .withDescription("Study for MA2101").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 11, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 3))
            .build();
    public static final Event STUDY_WITH_JANE_DAY_FOUR = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(5))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(2))
            .withEventName("Study with Jane")
            .withStartDateTime(LocalDateTime.of(2018, 1, 6, 10, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 6, 11, 0))
            .withDescription("Study for MA2101").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 11, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 3))
            .build();
    public static final Event STUDY_WITH_JILL_DAY_ONE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(6))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(3))
            .withEventName("Study with Jill")
            .withStartDateTime(LocalDateTime.of(2018, 1, 3, 11, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 3, 12, 0))
            .withDescription("Study for CS2103").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 12, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList())
            .build();
    public static final Event STUDY_WITH_JILL_DAY_TWO = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(7))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(3))
            .withEventName("Study with Jill")
            .withStartDateTime(LocalDateTime.of(2018, 1, 4, 11, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 4, 12, 0))
            .withDescription("Study for CS2103").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 12, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList())
            .build();
    public static final Event STUDY_WITH_JILL_DAY_THREE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(8))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(3))
            .withEventName("Study with Jill")
            .withStartDateTime(LocalDateTime.of(2018, 1, 5, 11, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 5, 12, 0))
            .withDescription("Study for CS2103").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 12, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList())
            .build();
    public static final Event STUDY_WITH_JILL_DAY_FOUR = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(9))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(3))
            .withEventName("Study with Jill")
            .withStartDateTime(LocalDateTime.of(2018, 1, 6, 11, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 6, 12, 0))
            .withDescription("Study for CS2103").withVenue("NUS")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 6, 12, 0))
            .withTags("Study", "School")
            .withReminderDurationList(getReminderDurationList())
            .build();

    // weekly event
    public static final Event SATURDAY_LECTURE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS9999")
            .withStartDateTime(LocalDateTime.of(2018, 11, 30, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 11, 30, 18, 0))
            .withDescription("CS9999 Description").withVenue("LT99")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event FRIDAY_LECTURE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS2103")
            .withStartDateTime(LocalDateTime.of(2018, 11, 30, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 11, 30, 18, 0))
            .withDescription("CS2103 Description").withVenue("I3")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event THURDSDAY_LECTURE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS0000")
            .withStartDateTime(LocalDateTime.of(2018, 11, 29, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 11, 29, 18, 0))
            .withDescription("CS0000 Description").withVenue("LT16")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 11, 28, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 11, 28, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT18")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED_ONE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 5, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 5, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT19")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED_TWO = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 12, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 12, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT19")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED_THREE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 19, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 19, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT19")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED_FOUR = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 26, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 26, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT19")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final List<Event> WEDNESDAY_LECTURE_UPDATED = new ArrayList<>(
            Arrays.asList(WEDNESDAY_LECTURE_UPDATED_ONE,
                    WEDNESDAY_LECTURE_UPDATED_TWO,
                    WEDNESDAY_LECTURE_UPDATED_THREE,
                    WEDNESDAY_LECTURE_UPDATED_FOUR));
    public static final Event WEDNESDAY_LECTURE_UPDATED2_ONE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 5, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 5, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT20")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED2_TWO = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 12, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 12, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT20")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED2_THREE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 19, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 19, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT20")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final Event WEDNESDAY_LECTURE_UPDATED2_FOUR = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS1000")
            .withStartDateTime(LocalDateTime.of(2018, 12, 26, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 26, 18, 0))
            .withDescription("CS1000 Description").withVenue("LT20")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 30, 18, 1))
            .build();
    public static final List<Event> WEDNESDAY_LECTURE_UPDATED2 = new ArrayList<>(
            Arrays.asList(WEDNESDAY_LECTURE_UPDATED2_ONE,
                    WEDNESDAY_LECTURE_UPDATED2_TWO,
                    WEDNESDAY_LECTURE_UPDATED2_THREE,
                    WEDNESDAY_LECTURE_UPDATED2_FOUR));

    // monthly event
    public static final Event DINNER_WITH_JOE_WEEK_ONE = new EventBuilder()

            .withEventUid(CONSTANT_EVENTUID.get(10))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(4))
            .withEventName("Dinner with Joe")
            .withStartDateTime(LocalDateTime.of(2018, 3, 3, 3, 0))
            .withEndDateTime(LocalDateTime.of(2018, 3, 3, 4, 0))
            .withDescription("Catch up and dinner with Joe").withVenue("Singapore")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 17, 4, 0))
            .withTags("Relaxation", "Dinner")
            .withReminderDurationList(getReminderDurationList())
            .build();
    public static final Event DINNER_WITH_JOE_WEEK_TWO = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(11))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(4))
            .withEventName("Dinner with Joe")
            .withStartDateTime(LocalDateTime.of(2018, 3, 10, 3, 0))
            .withEndDateTime(LocalDateTime.of(2018, 3, 10, 4, 0))
            .withDescription("Catch up and dinner with Joe").withVenue("Singapore")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 17, 4, 0))
            .withTags("Relaxation", "Dinner")
            .withReminderDurationList(getReminderDurationList())
            .build();
    public static final Event DINNER_WITH_JOE_WEEK_THREE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(12))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(4))
            .withEventName("Dinner with Joe")
            .withStartDateTime(LocalDateTime.of(2018, 3, 17, 3, 0))
            .withEndDateTime(LocalDateTime.of(2018, 3, 17, 4, 0))
            .withDescription("Catch up and dinner with Joe").withVenue("Singapore")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 17, 4, 0))
            .withTags("Relaxation", "Dinner")
            .withReminderDurationList(getReminderDurationList())
            .build();

    // monthly event
    public static final Event STARTUP_LECTURE_MONTH_ONE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(13))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(5))
            .withEventName("Startup Lecture")
            .withStartDateTime(LocalDateTime.of(2018, 1, 28, 8, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 28, 10, 0))
            .withDescription("Lecture about entrepreneurship").withVenue("iCube")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 28, 10, 0))
            .withTags("Timetable", "Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 2, 3))
            .build();
    public static final Event STARTUP_LECTURE_MONTH_TWO = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(14))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(5))
            .withEventName("Startup Lecture")
            .withStartDateTime(LocalDateTime.of(2018, 2, 28, 8, 0))
            .withEndDateTime(LocalDateTime.of(2018, 2, 28, 10, 0))
            .withDescription("Lecture about entrepreneurship").withVenue("iCube")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 28, 10, 0))
            .withTags("Timetable", "Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 2, 3))
            .build();
    public static final Event STARTUP_LECTURE_MONTH_THREE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(15))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(5))
            .withEventName("Startup Lecture")
            .withStartDateTime(LocalDateTime.of(2018, 3, 28, 8, 0))
            .withEndDateTime(LocalDateTime.of(2018, 3, 28, 10, 0))
            .withDescription("Lecture about entrepreneurship").withVenue("iCube")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 28, 10, 0))
            .withTags("Timetable", "Study", "School")
            .withReminderDurationList(getReminderDurationList(0, 1, 2, 3))
            .build();

    // yearly event
    public static final Event LEAP_DAY_CELEBRATION_YEAR_ONE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(16))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(6))
            .withEventName("Leap Day Celebration")
            .withStartDateTime(LocalDateTime.of(2016, 2, 29, 0, 0))
            .withEndDateTime(LocalDateTime.of(2016, 2, 29, 1, 0))
            .withDescription("Celebrate a day that happens one in 4 years").withVenue("Marina Bay Sands")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 2, 29, 1, 0))
            .withTags("Celebration")
            .withReminderDurationList(getReminderDurationList(0))
            .build();
    public static final Event LEAP_DAY_CELEBRATION_YEAR_TWO = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(17))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(6))
            .withEventName("Leap Day Celebration")
            .withStartDateTime(LocalDateTime.of(2020, 2, 29, 0, 0))
            .withEndDateTime(LocalDateTime.of(2020, 2, 29, 1, 0))
            .withDescription("Celebrate a day that happens one in 4 years").withVenue("Marina Bay Sands")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 2, 29, 1, 0))
            .withTags("Celebration")
            .withReminderDurationList(getReminderDurationList(0))
            .build();
    public static final Event JIM_BIRTHDAY_YEAR_ONE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(18))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(7))
            .withEventName("Jim's Birthday")
            .withStartDateTime(LocalDateTime.of(2018, 12, 31, 0, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 31, 1, 0))
            .withDescription("Celebrate Jim's Birthday").withVenue("Jim's House")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 12, 31, 1, 0))
            .withTags("Celebration")
            .withReminderDurationList(getReminderDurationList(1, 2))
            .build();
    public static final Event JIM_BIRTHDAY_YEAR_TWO = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(19))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(7))
            .withEventName("Jim's Birthday")
            .withStartDateTime(LocalDateTime.of(2019, 12, 31, 0, 0))
            .withEndDateTime(LocalDateTime.of(2019, 12, 31, 1, 0))
            .withDescription("Celebrate Jim's Birthday").withVenue("Jim's House")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 12, 31, 1, 0))
            .withTags("Celebration")
            .withReminderDurationList(getReminderDurationList(1, 2))
            .build();
    public static final Event JIM_BIRTHDAY_YEAR_THREE = new EventBuilder()
            .withEventUid(CONSTANT_EVENTUID.get(20))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(7))
            .withEventName("Jim's Birthday")
            .withStartDateTime(LocalDateTime.of(2020, 12, 31, 0, 0))
            .withEndDateTime(LocalDateTime.of(2020, 12, 31, 1, 0))
            .withDescription("Celebrate Jim's Birthday").withVenue("Jim's House")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 12, 31, 1, 0))
            .withTags("Celebration")
            .withReminderDurationList(getReminderDurationList(1, 2))
            .build();

    // daily list
    public static final List<Event> STUDY_WITH_JANE_DAILY_LIST = List.of(STUDY_WITH_JANE_DAY_ONE,
            STUDY_WITH_JANE_DAY_TWO, STUDY_WITH_JANE_DAY_THREE, STUDY_WITH_JANE_DAY_FOUR);
    public static final List<Event> STUDY_WITH_JILL_DAILY_LIST = List.of(STUDY_WITH_JILL_DAY_ONE,
            STUDY_WITH_JILL_DAY_TWO, STUDY_WITH_JILL_DAY_THREE, STUDY_WITH_JILL_DAY_FOUR);

    // weekly list
    public static final List<Event> DINNER_WITH_JOE_WEEKLY_LIST = List.of(DINNER_WITH_JOE_WEEK_ONE,
            DINNER_WITH_JOE_WEEK_TWO, DINNER_WITH_JOE_WEEK_THREE);

    // monthly list
    public static final List<Event> STARTUP_LECTURE_MONTHLY_LIST = List.of(STARTUP_LECTURE_MONTH_ONE,
            STARTUP_LECTURE_MONTH_TWO, STARTUP_LECTURE_MONTH_THREE);

    // yearly list
    public static final List<Event> LEAP_DAY_CELEBRATION_YEARLY_LIST = List.of(LEAP_DAY_CELEBRATION_YEAR_ONE,
            LEAP_DAY_CELEBRATION_YEAR_TWO);
    public static final List<Event> JIM_BIRTHDAY_YEARLY_LIST = List.of(JIM_BIRTHDAY_YEAR_ONE,
            JIM_BIRTHDAY_YEAR_TWO, JIM_BIRTHDAY_YEAR_THREE);

    // Manually added
    public static final Event AD_HOC_WORK = new EventBuilder().withEventUid(CONSTANT_EVENTUID.get(21))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(8))
            .withEventName("Ad hoc work").withStartDateTime(LocalDateTime.of(2018, 12, 12, 0, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 12, 1, 0))
            .withDescription("Ad hoc part time work").withVenue("Starbucks")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 12, 1, 0))
            .withTags("Work").withReminderDurationList(getReminderDurationList(3)).build();
    public static final Event ONE_TIME_JOB = new EventBuilder().withEventUid(CONSTANT_EVENTUID.get(22))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(9))
            .withEventName("One time job").withStartDateTime(LocalDateTime.of(2018, 6, 1, 0, 0))
            .withEndDateTime(LocalDateTime.of(2018, 6, 1, 1, 0))
            .withDescription("One time job and get paid").withVenue("McDonald's")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 6, 1, 1, 0))
            .withTags("Work").withReminderDurationList(getReminderDurationList(3)).build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event MA2101_JANUARY_1_2018_YEARLY = new EventBuilder().withEventUid(CONSTANT_EVENTUID.get(23))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(10))
            .withEventName(VALID_EVENT_NAME_MA2101).withStartDateTime(VALID_START_DATETIME_MA2101)
            .withEndDateTime(VALID_END_DATETIME_MA2101).withDescription(VALID_DESCRIPTION_MA2101)
            .withVenue(VALID_VENUE_MA2101).withRepeatType(VALID_REPEAT_TYPE_MA2101)
            .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101).withTags(VALID_TAG_SCHOOL)
            .withReminderDurationList(getReminderDurationList(3)).build();

    public static final Event MA3220_JANUARY_1_2019_SINGLE = new EventBuilder().withEventUid(CONSTANT_EVENTUID.get(24))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(11))
            .withEventName(VALID_EVENT_NAME_MA3220).withStartDateTime(VALID_START_DATETIME_MA3220)
            .withEndDateTime(VALID_END_DATETIME_MA3220).withDescription(VALID_DESCRIPTION_MA3220)
            .withVenue(VALID_VENUE_MA3220).withRepeatType(VALID_REPEAT_TYPE_MA3220)
            .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA3220).withTags(VALID_TAG_PLAY)
            .withReminderDurationList(getReminderDurationList(3)).build();

    public static final Event CS2103_LECTURE = new EventBuilder()
            .withEventUid(UUID.randomUUID())
            .withEventSetUid(UUID.randomUUID())
            .withEventName("CS2103")
            .withStartDateTime(LocalDateTime.of(2018, 8, 17, 16, 0))
            .withEndDateTime(LocalDateTime.of(2018, 8, 17, 18, 0))
            .withDescription("CS2103 Description").withVenue("I3")
            .withRepeatType(RepeatType.WEEKLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 11, 16, 18, 1))
            .build();

    public static final Event CS2103_WEEKLY = new EventBuilder().withEventUid(CONSTANT_EVENTUID.get(11))
            .withEventSetUid(CONSTANT_EVENTSETUID.get(11))
            .withEventName(VALID_EVENT_NAME_CS2103).withStartDateTime(VALID_START_DATETIME_CS2103)
            .withEndDateTime(VALID_END_DATETIME_CS2103).withDescription(VALID_DESCRIPTION_CS2103)
            .withVenue(VALID_VENUE_CS2103).withRepeatType(VALID_REPEAT_TYPE_CS2103)
            .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_CS2103)
            .withReminderDurationList(getReminderDurationList(3)).build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code Scheduler} with all the typical events.
     */
    public static Scheduler getTypicalScheduler() {
        Scheduler scheduler = new Scheduler();
        for (Event event : getTypicalEvents()) {
            scheduler.addEvent(event);
        }
        return scheduler;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(DISCUSSION_WITH_JACK, INTERVIEW_WITH_JOHN, STUDY_WITH_JANE_DAY_ONE,
                STUDY_WITH_JANE_DAY_TWO, STUDY_WITH_JANE_DAY_THREE, STUDY_WITH_JANE_DAY_FOUR,
                STUDY_WITH_JILL_DAY_ONE, STUDY_WITH_JILL_DAY_TWO, STUDY_WITH_JILL_DAY_THREE,
                STUDY_WITH_JILL_DAY_FOUR, DINNER_WITH_JOE_WEEK_ONE, DINNER_WITH_JOE_WEEK_TWO,
                DINNER_WITH_JOE_WEEK_THREE, JIM_BIRTHDAY_YEAR_ONE, JIM_BIRTHDAY_YEAR_TWO,
                JIM_BIRTHDAY_YEAR_THREE, LEAP_DAY_CELEBRATION_YEAR_ONE, LEAP_DAY_CELEBRATION_YEAR_TWO,
                STARTUP_LECTURE_MONTH_ONE, STARTUP_LECTURE_MONTH_TWO, STARTUP_LECTURE_MONTH_THREE));
    }

    public static List<Event> getStudyWithJaneAllList() {
        return new ArrayList<>(Arrays.asList(STUDY_WITH_JANE_DAY_ONE,
                STUDY_WITH_JANE_DAY_TWO, STUDY_WITH_JANE_DAY_THREE, STUDY_WITH_JANE_DAY_FOUR));
    }

    public static List<Event> getBirthdayAllList() {
        return new ArrayList<>(Arrays.asList(JIM_BIRTHDAY_YEAR_ONE, JIM_BIRTHDAY_YEAR_TWO,
                JIM_BIRTHDAY_YEAR_THREE));
    }

    /**
     * Returns an {@code Scheduler} with all Study With Jane
     */
    public static Scheduler getSchedulerStudyWithJane() {
        Scheduler scheduler = new Scheduler();
        for (Event event : getStudyWithJaneAllList()) {
            scheduler.addEvent(event);
        }
        return scheduler;
    }

    /**
     * Returns an {@code Scheduler} with all JIM BIRTHDAY
     */
    public static Scheduler getSchedulerBirthday() {
        Scheduler scheduler = new Scheduler();
        for (Event event : getBirthdayAllList()) {
            scheduler.addEvent(event);
        }
        return scheduler;
    }

}
