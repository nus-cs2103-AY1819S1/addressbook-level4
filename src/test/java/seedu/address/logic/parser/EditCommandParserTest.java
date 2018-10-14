package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REPEAT_TYPE_DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.REPEAT_TYPE_DESC_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.REPEAT_UNTIL_DATETIME_DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_DATETIME_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_TYPE_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPEAT_UNTIL_DATETIME_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_DATETIME_MA3220;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_PLAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_MA2101;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_MA2101;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditEventDescriptor;
import seedu.address.model.event.EventName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditEventDescriptorBuilder;

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
        assertParseFailure(parser, "-5" + EVENT_NAME_DESC_MA2101, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_NAME_DESC_MA2101, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid event name followed by valid descrription
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + DESCRIPTION_DESC_MA2101,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // valid event name followed by invalid event name. The test case for invalid event name followed
        // by valid event name
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + EVENT_NAME_DESC_MA2101 + INVALID_EVENT_NAME_DESC,
                EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Event} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_SCHOOL + TAG_DESC_PLAY + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_SCHOOL + TAG_EMPTY + TAG_DESC_PLAY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_SCHOOL + TAG_DESC_PLAY, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME_DESC + START_DATETIME_DESC_MA2101
                        + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                        + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                        + INVALID_TAG_DESC, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101
                + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                + TAG_DESC_PLAY + TAG_DESC_SCHOOL;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MA2101)
                .withStartDateTime(VALID_START_DATETIME_MA2101).withEndDateTime(VALID_END_DATETIME_MA2101)
                .withDescription(VALID_DESCRIPTION_MA2101)
                .withVenue(VALID_VENUE_MA2101).withRepeatType(VALID_REPEAT_TYPE_MA2101)
                .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101)
                .withTags(VALID_TAG_PLAY, VALID_TAG_SCHOOL).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_MA3220 + REPEAT_TYPE_DESC_MA3220;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_MA3220)
                .withRepeatType(VALID_REPEAT_TYPE_MA3220).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_DESC_MA2101;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start date time
        userInput = targetIndex.getOneBased() + START_DATETIME_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder().withStartDateTime(VALID_START_DATETIME_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end date time
        userInput = targetIndex.getOneBased() + END_DATETIME_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder().withEndDateTime(VALID_END_DATETIME_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // venue
        userInput = targetIndex.getOneBased() + VENUE_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder().withVenue(VALID_VENUE_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // repeat type
        userInput = targetIndex.getOneBased() + REPEAT_TYPE_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder().withRepeatType(VALID_REPEAT_TYPE_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // repeat until date time
        userInput = targetIndex.getOneBased() + REPEAT_UNTIL_DATETIME_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder()
                .withRepeatUntilDateTime(VALID_REPEAT_UNTIL_DATETIME_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_SCHOOL;
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
                + DESCRIPTION_DESC_MA3220 + TAG_DESC_SCHOOL;

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
        String userInput = targetIndex.getOneBased() + INVALID_EVENT_NAME_DESC + EVENT_NAME_DESC_MA2101;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_EVENT_NAME_MA2101).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_EVENT_NAME_DESC + EVENT_NAME_DESC_MA2101
                + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101;
        descriptor = new EditEventDescriptorBuilder().withEventName(VALID_EVENT_NAME_MA2101)
                .withDescription(VALID_DESCRIPTION_MA2101).withVenue(VALID_VENUE_MA2101).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
