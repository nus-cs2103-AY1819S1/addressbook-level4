package seedu.scheduler.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_EVENT_REMINDER_DURATION;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_TYPE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_REPEAT_UNTIL_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_VENUE;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.CommandHistory;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.Scheduler;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventNameContainsKeywordsPredicate;
import seedu.scheduler.model.event.ReminderDurationList;
import seedu.scheduler.model.event.RepeatType;
import seedu.scheduler.model.util.SampleSchedulerDataUtil;
import seedu.scheduler.testutil.EditEventDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final UUID VALID_EVENT_UID_CS2103 = UUID.randomUUID();
    public static final UUID VALID_EVENT_UID_MA2101 = UUID.fromString("1bae658a-c457-4b83-8319-d45f8e61027d");
    public static final UUID VALID_EVENT_UID_MA3220 = UUID.fromString("aef6b77e-20a7-4de4-8402-343fbd475fd4");
    public static final UUID VALID_EVENT_UUID_CS2103 = UUID.randomUUID();
    public static final UUID VALID_EVENT_UUID_MA2101 = UUID.fromString("1bae658a-c457-4b83-8319-d45f8e61028d");
    public static final UUID VALID_EVENT_UUID_MA3220 = UUID.fromString("aef6b77e-20a7-4de4-8402-343fbd475fd3");
    public static final String VALID_EVENT_NAME_CS2103 = "CS2103";
    public static final String VALID_EVENT_NAME_MA2101 = "MA2101";
    public static final String VALID_EVENT_NAME_MA3220 = "MA3220";
    public static final LocalDateTime VALID_START_DATETIME_CS2103 = LocalDateTime.of(2018, 8,
            17, 16, 0);
    public static final LocalDateTime VALID_START_DATETIME_MA2101 = LocalDateTime.of(2018, 1,
            1, 1, 1);
    public static final LocalDateTime VALID_START_DATETIME_MA3220 = LocalDateTime.of(2019, 2,
            2, 2, 2);
    public static final LocalDateTime VALID_END_DATETIME_CS2103 = LocalDateTime.of(2018, 8,
            17, 18, 0);
    public static final LocalDateTime VALID_END_DATETIME_MA2101 = LocalDateTime.of(2018, 1,
            1, 1, 2);
    public static final LocalDateTime VALID_END_DATETIME_MA3220 = LocalDateTime.of(2019, 2,
            2, 2, 3);
    public static final String VALID_DESCRIPTION_CS2103 = "CS2103 Description";
    public static final String VALID_DESCRIPTION_MA2101 = "MA2101";
    public static final String VALID_DESCRIPTION_MA3220 = "MA3220";
    public static final String VALID_VENUE_CS2103 = "I3";
    public static final String VALID_VENUE_MA2101 = "S17";
    public static final String VALID_VENUE_MA3220 = "LT20";
    public static final RepeatType VALID_REPEAT_TYPE_CS2103 = RepeatType.WEEKLY;
    public static final RepeatType VALID_REPEAT_TYPE_MA2101 = RepeatType.YEARLY;
    public static final RepeatType VALID_REPEAT_TYPE_MA3220 = RepeatType.NONE;
    public static final LocalDateTime VALID_REPEAT_UNTIL_DATETIME_CS2103 = LocalDateTime.of(2018, 11,
            16, 18, 1);
    public static final LocalDateTime VALID_REPEAT_UNTIL_DATETIME_MA2101 = LocalDateTime.of(2019, 1,
            1, 1, 2);
    public static final LocalDateTime VALID_REPEAT_UNTIL_DATETIME_MA3220 = LocalDateTime.of(2019, 2,
            2, 2, 3);
    public static final String VALID_TAG_SCHOOL = "school";
    public static final String VALID_TAG_PLAY = "play";
    public static final String VALID_TAG_UNUSED = "unused"; // do not use this tag when creating an event
    public static final String VALID_DURATION_1H = "1H";
    public static final String VALID_DURATION_30M = "30M";
    public static final ReminderDurationList VALID_DURATION_LIST_1H = SampleSchedulerDataUtil.getReminderDurationList(
            3);
    public static final ReminderDurationList VALID_DURATION_LIST_30M = SampleSchedulerDataUtil.getReminderDurationList(
            1);
    public static final String EVENT_NAME_DESC_CS2103 = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_CS2103;
    public static final String EVENT_NAME_DESC_MA2101 = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_MA2101;
    public static final String EVENT_NAME_DESC_MA3220 = " " + PREFIX_EVENT_NAME + VALID_EVENT_NAME_MA3220;
    public static final String START_DATETIME_DESC_CS2103 = " " + PREFIX_START_DATE_TIME + VALID_START_DATETIME_CS2103;
    public static final String START_DATETIME_DESC_MA2101 = " " + PREFIX_START_DATE_TIME + VALID_START_DATETIME_MA2101;
    public static final String START_DATETIME_DESC_MA3220 = " " + PREFIX_START_DATE_TIME + VALID_START_DATETIME_MA3220;
    public static final String END_DATETIME_DESC_CS2103 = " " + PREFIX_END_DATE_TIME + VALID_END_DATETIME_CS2103;
    public static final String END_DATETIME_DESC_MA2101 = " " + PREFIX_END_DATE_TIME + VALID_END_DATETIME_MA2101;
    public static final String END_DATETIME_DESC_MA3220 = " " + PREFIX_END_DATE_TIME + VALID_END_DATETIME_MA3220;
    public static final String DESCRIPTION_DESC_CS2103 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_CS2103;
    public static final String DESCRIPTION_DESC_MA2101 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_MA2101;
    public static final String DESCRIPTION_DESC_MA3220 = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_MA3220;
    public static final String VENUE_DESC_CS2103 = " " + PREFIX_VENUE + VALID_VENUE_CS2103;
    public static final String VENUE_DESC_MA2101 = " " + PREFIX_VENUE + VALID_VENUE_MA2101;
    public static final String VENUE_DESC_MA3220 = " " + PREFIX_VENUE + VALID_VENUE_MA3220;
    public static final String REPEAT_TYPE_DESC_CS2103 = " " + PREFIX_REPEAT_TYPE + VALID_REPEAT_TYPE_CS2103;
    public static final String REPEAT_TYPE_DESC_MA2101 = " " + PREFIX_REPEAT_TYPE + VALID_REPEAT_TYPE_MA2101;
    public static final String REPEAT_TYPE_DESC_MA3220 = " " + PREFIX_REPEAT_TYPE + VALID_REPEAT_TYPE_MA3220;
    public static final String REPEAT_UNTIL_DATETIME_DESC_CS2103 = " " + PREFIX_REPEAT_UNTIL_DATE_TIME
            + VALID_REPEAT_UNTIL_DATETIME_CS2103;
    public static final String REPEAT_UNTIL_DATETIME_DESC_MA2101 = " " + PREFIX_REPEAT_UNTIL_DATE_TIME
            + VALID_REPEAT_UNTIL_DATETIME_MA2101;
    public static final String REPEAT_UNTIL_DATETIME_DESC_MA3220 = " " + PREFIX_REPEAT_UNTIL_DATE_TIME
            + VALID_REPEAT_UNTIL_DATETIME_MA3220;
    public static final String TAG_DESC_SCHOOL = " " + PREFIX_TAG + VALID_TAG_SCHOOL;
    public static final String TAG_DESC_PLAY = " " + PREFIX_TAG + VALID_TAG_PLAY;
    public static final String REMINDER_DURATION_LIST_1H = " " + PREFIX_EVENT_REMINDER_DURATION + VALID_DURATION_1H;
    public static final String REMINDER_DURATION_LIST_30M = " " + PREFIX_EVENT_REMINDER_DURATION + VALID_DURATION_30M;
    // empty string not allowed in event names
    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_EVENT_NAME + "  ";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String[] INVALID_START_END_DATETIMES = {" " + PREFIX_START_DATE_TIME + "2018-01-02",
        " " + PREFIX_END_DATE_TIME + "2018-01-01"};

    public static final String VALID_FLAG_ALL = " " + FLAG_ALL;
    public static final String VALID_FLAG_UPCOMING = " " + FLAG_UPCOMING;

    public static final String POSTFIX_ALL = " -a";
    public static final String POSTFIX_UPCOMING = " -u";
    public static final String POSTFIX_INVALID = " -i";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditEventDescriptor DESC_MA2101;
    public static final EditCommand.EditEventDescriptor DESC_MA3220;

    static {
        DESC_MA2101 = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MA2101)
                .withStartDateTime(VALID_START_DATETIME_MA2101).withEndDateTime(VALID_END_DATETIME_MA2101)
                .withDescription(VALID_DESCRIPTION_MA2101).withVenue(VALID_VENUE_MA2101)
                .withRepeatType(VALID_REPEAT_TYPE_MA2101).withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101)
                .withTags(VALID_TAG_PLAY).withReminderDurationList(VALID_DURATION_LIST_1H).build();
        DESC_MA3220 = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MA3220)
                .withStartDateTime(VALID_START_DATETIME_MA3220).withEndDateTime(VALID_END_DATETIME_MA3220)
                .withDescription(VALID_DESCRIPTION_MA3220).withVenue(VALID_VENUE_MA3220)
                .withRepeatType(VALID_REPEAT_TYPE_MA3220).withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA3220)
                .withTags(VALID_TAG_SCHOOL).withReminderDurationList(VALID_DURATION_LIST_1H).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the scheduler book and the filtered event list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Scheduler expectedScheduler = new Scheduler(actualModel.getScheduler());
        List<Event> expectedFilteredList = new ArrayList<>(actualModel.getFilteredEventList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedScheduler, actualModel.getScheduler());
            assertEquals(expectedFilteredList, actualModel.getFilteredEventList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetIndex} in the
     * {@code model}'s scheduler.
     */
    public static void showEventAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredEventList().size());

        Event event = model.getFilteredEventList().get(targetIndex.getZeroBased());
        final String[] splitEventName = event.getEventName().value.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitEventName[0])));

        assertEquals(1, model.getFilteredEventList().size());
    }

    /**
     * Deletes the first event in {@code model}'s filtered list from {@code model}'s scheduler.
     */
    public static void deleteFirstEvent(Model model) {
        Event firstEvent = model.getFilteredEventList().get(0);
        model.deleteEvent(firstEvent);
        model.commitScheduler();
    }

}
