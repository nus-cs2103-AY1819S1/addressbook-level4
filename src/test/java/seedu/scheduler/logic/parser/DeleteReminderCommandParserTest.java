package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.CommandTestUtil.REMINDER_DURATION_LIST_1H;
import static seedu.scheduler.logic.commands.CommandTestUtil.REMINDER_DURATION_LIST_1H30M;
import static seedu.scheduler.logic.commands.CommandTestUtil.REMINDER_DURATION_LIST_30M;
import static seedu.scheduler.logic.commands.CommandTestUtil.REMINDER_DURATION_LIST_EMPTY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FLAG_ALL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FLAG_UPCOMING;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_ALL;
import static seedu.scheduler.logic.parser.CliSyntax.FLAG_UPCOMING;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.scheduler.model.util.SampleSchedulerDataUtil.getReminderDurationList;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIFTH_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.DeleteReminderCommand;
import seedu.scheduler.model.event.ReminderDurationList;

public class DeleteReminderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE);

    private DeleteReminderCommandParser parser = new DeleteReminderCommandParser();


    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, REMINDER_DURATION_LIST_1H, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // If only specify index, it will throw CommandFailure
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + REMINDER_DURATION_LIST_1H, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMINDER_DURATION_LIST_1H, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string" + REMINDER_DURATION_LIST_1H, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid duration
        assertParseFailure(parser, "1" + " re/30.0S6H" + VALID_FLAG_ALL,
                ReminderDurationList.MESSAGE_DURATION_CONSTRAINTS);
        assertParseFailure(parser, "1" + " re/30D" + VALID_FLAG_ALL,
                ReminderDurationList.MESSAGE_DURATION_CONSTRAINTS);

        // multiple flags
        assertParseFailure(parser, "1" + " re/30M" + VALID_FLAG_ALL + VALID_FLAG_UPCOMING,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleRemindersSingleEvent_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + REMINDER_DURATION_LIST_30M + REMINDER_DURATION_LIST_1H;

        DeleteReminderCommand expectedCommand = new DeleteReminderCommand(targetIndex,
                getReminderDurationList(1, 3));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_singleRemindersAllEvent_success() {
        Index targetIndex = INDEX_FIFTH_EVENT;
        String userInput = targetIndex.getOneBased() + REMINDER_DURATION_LIST_30M + VALID_FLAG_ALL;

        DeleteReminderCommand expectedCommand = new DeleteReminderCommand(targetIndex,
                getReminderDurationList(1), FLAG_ALL);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRemindersUpcomingEvent_success() {
        Index targetIndex = INDEX_FIFTH_EVENT;
        String userInput = targetIndex.getOneBased() + REMINDER_DURATION_LIST_30M
                + REMINDER_DURATION_LIST_1H30M + VALID_FLAG_UPCOMING;

        DeleteReminderCommand expectedCommand = new DeleteReminderCommand(targetIndex,
                getReminderDurationList(1, 2), FLAG_UPCOMING);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyRemindersUpcomingEvent_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + REMINDER_DURATION_LIST_EMPTY;

        DeleteReminderCommand expectedCommand = new DeleteReminderCommand(targetIndex,
                getReminderDurationList());

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
