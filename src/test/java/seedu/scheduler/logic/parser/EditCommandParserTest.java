package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.REMINDER_DURATION_LIST_1H;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_TYPE_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_UNTIL_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_DURATION_LIST_1H;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FLAG_ALL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FLAG_UPCOMING;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_VENUE_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.VENUE_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VENUE_DESC_MA3220;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.scheduler.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Test;

import seedu.scheduler.commons.core.index.Index;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EditCommand.EditEventDescriptor;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.testutil.EditEventDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_EVENT_NAME_MA2101, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENT_NAME_DESC_MA2101 + VALID_FLAG_ALL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_NAME_DESC_MA2101 + VALID_FLAG_ALL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + VALID_FLAG_ALL,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC + VALID_FLAG_ALL, Tag.MESSAGE_CONSTRAINTS);

        // invalid event name followed by valid description
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + DESCRIPTION_DESC_MA2101 + VALID_FLAG_ALL,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // valid event name followed by invalid event name. The test case for invalid event name followed
        // by valid event name
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + EVENT_NAME_DESC_MA2101 + INVALID_EVENT_NAME_DESC + VALID_FLAG_ALL,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Event} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_SCHOOL + TAG_DESC_PLAY + TAG_EMPTY + VALID_FLAG_ALL,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_SCHOOL + TAG_EMPTY + TAG_DESC_PLAY + VALID_FLAG_ALL,
                Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_SCHOOL + TAG_DESC_PLAY + VALID_FLAG_ALL,
                Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + START_DATETIME_DESC_MA2101
                        + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                        + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                        + INVALID_TAG_DESC + VALID_FLAG_ALL, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_multipleFlag_failure() {
        assertParseFailure(parser, "1" + EVENT_NAME_DESC_MA2101 + VALID_FLAG_ALL + VALID_FLAG_UPCOMING,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101
                + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                + TAG_DESC_PLAY + TAG_DESC_SCHOOL + REMINDER_DURATION_LIST_1H + VALID_FLAG_ALL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MA2101)
                .withStartDateTime(VALID_START_DATETIME_MA2101).withEndDateTime(VALID_END_DATETIME_MA2101)
                .withDescription(VALID_DESCRIPTION_MA2101)
                .withVenue(VALID_VENUE_MA2101).withRepeatType(VALID_REPEAT_TYPE_MA2101)
                .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101)
                .withTags(VALID_TAG_PLAY, VALID_TAG_SCHOOL).withReminderDurationList(VALID_DURATION_LIST_1H).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_MA3220 + VENUE_DESC_MA3220 + VALID_FLAG_ALL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_MA3220)
                .withVenue(VALID_VENUE_MA3220).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_MA2101 + VALID_FLAG_ALL;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date time
        userInput = targetIndex.getOneBased() + START_DATETIME_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATETIME_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date time
        userInput = targetIndex.getOneBased() + END_DATETIME_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withEndDateTime(VALID_END_DATETIME_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + VENUE_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withVenue(VALID_VENUE_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // repeat type
        userInput = targetIndex.getOneBased() + REPEAT_TYPE_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withRepeatType(VALID_REPEAT_TYPE_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // repeat until date time
        userInput = targetIndex.getOneBased() + REPEAT_UNTIL_DATETIME_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder()
                .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_SCHOOL + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withTags(VALID_TAG_SCHOOL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101
                + DESCRIPTION_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101 + TAG_DESC_PLAY
                + DESCRIPTION_DESC_MA2101 + START_DATETIME_DESC_MA3220 + END_DATETIME_DESC_MA3220
                + DESCRIPTION_DESC_MA3220 + TAG_DESC_SCHOOL + VALID_FLAG_ALL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withStartDateTime(VALID_START_DATETIME_MA3220).withEndDateTime(VALID_END_DATETIME_MA3220)
                .withDescription(VALID_DESCRIPTION_MA3220).withTags(VALID_TAG_PLAY, VALID_TAG_SCHOOL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_EVENT_NAME_DESC + EVENT_NAME_DESC_MA2101
                + VALID_FLAG_ALL;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_EVENT_NAME_DESC + EVENT_NAME_DESC_MA2101
                + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101 + VALID_FLAG_ALL;
        descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MA2101)
                .withDescription(VALID_DESCRIPTION_MA2101).withVenue(VALID_VENUE_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY + VALID_FLAG_ALL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
