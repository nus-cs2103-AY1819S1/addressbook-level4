package seedu.scheduler.testutil;

import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA3220;

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

    public static final String KEYWORD_MATCHING_JANUARY = "January"; // A keyword that matches January

    private static final UUID CONSTANT_UUID = UUID.fromString("066db0fd-0bd2-423f-aef4-fd1f8d30a625");

    // single event
    public static final Event JANUARY_1_2018_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01 January 2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 1, 2, 0))
            .withDescription("01 January 2018").withVenue("Computing")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 1, 2, 0))
            .withTags("play", "home").build();
    public static final Event JANUARY_2_2018_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("02 January 2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 2, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 2, 2, 0))
            .withDescription("02 January 2018").withVenue("Science")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 2, 2, 0))
            .withTags("play").build();
    public static final Event JANUARY_3_2018_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("03 January 2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 3, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 3, 2, 0))
            .withDescription("03 January 2018").withVenue("Arts")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 3, 2, 0))
            .withTags("home").build();

    // daily event
    public static final Event JANUARY_30_2018_DAILY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("30-01-2018 - 01-02-2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 30, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 30, 2, 0))
            .withDescription("30-01-2018 - 01-02-2018").withVenue("Computing")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 2, 1, 2, 0))
            .build();
    public static final Event JANUARY_31_2018_DAILY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("30-01-2018 - 01-02-2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 31, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 31, 2, 0))
            .withDescription("30-01-2018 - 01-02-2018").withVenue("Computing")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 2, 1, 2, 0))
            .build();
    public static final Event FEBRUARY_1_2018_DAILY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("30-01-2018 - 01-02-2018")
            .withStartDateTime(LocalDateTime.of(2018, 2, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 2, 1, 2, 0))
            .withDescription("30-01-2018 - 01-02-2018").withVenue("Computing")
            .withRepeatType(RepeatType.DAILY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 2, 1, 2, 0))
            .build();

    // monthly event
    public static final Event JANUARY_1_2018_MONTHLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01-01-2018 - 01-03-2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 1, 2, 0))
            .withDescription("01-01-2018 - 01-03-2018").withVenue("Computing")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 1, 2, 0))
            .build();
    public static final Event FEBRUARY_1_2018_MONTHLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01-01-2018 - 01-03-2018")
            .withStartDateTime(LocalDateTime.of(2018, 2, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 2, 1, 2, 0))
            .withDescription("01-01-2018 - 01-03-2018").withVenue("Computing")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 1, 2, 0))
            .build();
    public static final Event MARCH_1_2018_MONTHLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01-01-2018 - 01-03-2018")
            .withStartDateTime(LocalDateTime.of(2018, 3, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 3, 1, 2, 0))
            .withDescription("01-01-2018 - 01-03-2018").withVenue("Computing")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 1, 2, 0))
            .build();
    public static final Event JANUARY_31_2018_MONTHLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("31-01-2018 - 31-03-2018")
            .withStartDateTime(LocalDateTime.of(2018, 1, 31, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 31, 2, 0))
            .withDescription("31-01-2018 - 31-03-2018").withVenue("Computing")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 31, 2, 0))
            .build();
    public static final Event MARCH_31_2018_MONTHLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("31-01-2018 - 31-03-2018")
            .withStartDateTime(LocalDateTime.of(2018, 3, 31, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 3, 31, 2, 0))
            .withDescription("31-01-2018 - 31-03-2018").withVenue("Computing")
            .withRepeatType(RepeatType.MONTHLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2018, 3, 31, 2, 0))
            .build();

    // yearly event
    public static final Event JANUARY_1_2018_YEARLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01-01-2018 - 01-01-2020")
            .withStartDateTime(LocalDateTime.of(2018, 1, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 1, 2, 0))
            .withDescription("01-01-2018 - 01-01-2020").withVenue("Computing")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 1, 1, 2, 0))
            .build();
    public static final Event JANUARY_1_2019_YEARLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01-01-2018 - 01-01-2020")
            .withStartDateTime(LocalDateTime.of(2019, 1, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2019, 1, 1, 2, 0))
            .withDescription("01-01-2018 - 01-01-2020").withVenue("Computing")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 1, 1, 2, 0))
            .build();
    public static final Event JANUARY_1_2020_YEARLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("01-01-2018 - 01-01-2020")
            .withStartDateTime(LocalDateTime.of(2020, 1, 1, 1, 0))
            .withEndDateTime(LocalDateTime.of(2020, 1, 1, 2, 0))
            .withDescription("01-01-2018 - 01-01-2020").withVenue("Computing")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 1, 1, 2, 0))
            .build();
    public static final Event FEBRUARY_29_2016_YEARLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("29-02-2016 - 29-02-2020")
            .withStartDateTime(LocalDateTime.of(2016, 2, 29, 1, 0))
            .withEndDateTime(LocalDateTime.of(2016, 2, 29, 2, 0))
            .withDescription("29-02-2016 - 29-02-2020").withVenue("Computing")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 2, 29, 2, 0))
            .build();
    public static final Event FEBRUARY_29_2020_YEARLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("29-02-2016 - 29-02-2020")
            .withStartDateTime(LocalDateTime.of(2020, 2, 29, 1, 0))
            .withEndDateTime(LocalDateTime.of(2020, 2, 29, 2, 0))
            .withDescription("29-02-2016 - 29-02-2020").withVenue("Computing")
            .withRepeatType(RepeatType.YEARLY)
            .withRepeatUntilDateTime(LocalDateTime.of(2020, 2, 29, 2, 0))
            .build();

    // daily list
    public static final List<Event> DAY2018_THREE_DAY_LIST = List.of(JANUARY_30_2018_DAILY,
            JANUARY_31_2018_DAILY, FEBRUARY_1_2018_DAILY);

    // monthly list
    public static final List<Event> MONTH2018_1_THREE_MONTH_LIST = List.of(JANUARY_1_2018_MONTHLY,
            FEBRUARY_1_2018_MONTHLY, MARCH_1_2018_MONTHLY);
    public static final List<Event> MONTH2018_31_THREE_MONTH_LIST = List.of(JANUARY_31_2018_MONTHLY,
            MARCH_31_2018_MONTHLY);

    // yearly list
    public static final List<Event> JANUARY_1_THREE_YEAR_LIST = List.of(JANUARY_1_2018_YEARLY,
            JANUARY_1_2019_YEARLY, JANUARY_1_2020_YEARLY);
    public static final List<Event> FEBRUARY_29_FOUR_YEAR_LIST = List.of(FEBRUARY_29_2016_YEARLY,
            FEBRUARY_29_2020_YEARLY);

    // Manually added
    public static final Event WORK_DECEMBER_12_2018_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("work").withStartDateTime(LocalDateTime.of(2018, 12, 12, 0, 0))
            .withEndDateTime(LocalDateTime.of(2018, 12, 12, 1, 0))
            .withDescription("workAndWork").withVenue("Work")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 12, 12, 1, 0))
            .withTags("work").build();
    public static final Event TRAVEL_JUNE_1_2018_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("Travel").withStartDateTime(LocalDateTime.of(2018, 6, 1, 0, 0))
            .withEndDateTime(LocalDateTime.of(2018, 6, 1, 1, 0))
            .withDescription("travelOrTravel").withVenue("USA")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 6, 1, 1, 0))
            .withTags("travel").build();

    public static final Event PLAY_JANUARY_1_2018_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName("Play").withStartDateTime(LocalDateTime.of(2018, 1, 1, 0, 0))
            .withEndDateTime(LocalDateTime.of(2018, 1, 1, 1, 0))
            .withDescription("doAndPlay").withVenue("Home")
            .withRepeatType(RepeatType.NONE).withRepeatUntilDateTime(LocalDateTime.of(2018, 1, 1, 1, 0))
            .withTags("playMonth").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final Event MA2101_JANUARY_1_2018_YEARLY = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName(VALID_EVENT_NAME_MA2101).withStartDateTime(VALID_START_DATETIME_MA2101)
            .withEndDateTime(VALID_END_DATETIME_MA2101).withDescription(VALID_DESCRIPTION_MA2101)
            .withVenue(VALID_VENUE_MA2101).withRepeatType(VALID_REPEAT_TYPE_MA2101)
            .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101).withTags(VALID_TAG_SCHOOL).build();

    public static final Event MA3220_JANUARY_1_2019_SINGLE = new EventBuilder().withUuid(CONSTANT_UUID)
            .withEventName(VALID_EVENT_NAME_MA3220).withStartDateTime(VALID_START_DATETIME_MA3220)
            .withEndDateTime(VALID_END_DATETIME_MA3220).withDescription(VALID_DESCRIPTION_MA3220)
            .withVenue(VALID_VENUE_MA3220).withRepeatType(VALID_REPEAT_TYPE_MA3220)
            .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA3220).withTags(VALID_TAG_PLAY).build();

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
        return new ArrayList<>(Arrays.asList(JANUARY_1_2018_SINGLE, JANUARY_2_2018_SINGLE, JANUARY_3_2018_SINGLE,
                WORK_DECEMBER_12_2018_SINGLE, TRAVEL_JUNE_1_2018_SINGLE, PLAY_JANUARY_1_2018_SINGLE));
    }
}
