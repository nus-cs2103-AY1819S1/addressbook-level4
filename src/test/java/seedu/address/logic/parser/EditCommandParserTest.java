package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FREQUENCY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FREQUENCY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PRIORITY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PRIORITY_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREQUENCY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRIORITY_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Frequency;
import seedu.address.model.task.Name;
import seedu.address.model.task.Priority;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PRIORITY_DESC,
                Priority.MESSAGE_PRIORITY_CONSTRAINTS); // invalid priority
        assertParseFailure(parser, "1" + INVALID_FREQUENCY_DESC,
                Frequency.MESSAGE_FREQUENCY_CONSTRAINTS); // invalid frequency
        assertParseFailure(parser, "1" + INVALID_TAG_DESC,
                Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // valid priority followed by invalid priority. Fails because two priorities are specified.
        assertParseFailure(parser, "1" + PRIORITY_DESC_BOB + INVALID_PRIORITY_DESC, MESSAGE_INVALID_FORMAT);

        // valid frequency followed by invalid frequency. Fails because two frequencies are specified.
        assertParseFailure(parser, "1" + FREQUENCY_DESC_BOB + INVALID_FREQUENCY_DESC, MESSAGE_INVALID_FORMAT);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Task} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_NAME_DESC + INVALID_PRIORITY_DESC + INVALID_FREQUENCY_DESC,
                Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_TASK;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_BOB + FREQUENCY_DESC_BOB
                + TAG_DESC_HUSBAND + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPriority(VALID_PRIORITY_BOB)
                .withFrequency(VALID_FREQUENCY_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_BOB + FREQUENCY_DESC_BOB;

        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withPriority(VALID_PRIORITY_BOB)
                .withFrequency(VALID_FREQUENCY_BOB)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withName(VALID_NAME_AMY)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // priority
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withPriority(VALID_PRIORITY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // frequency
        userInput = targetIndex.getOneBased() + FREQUENCY_DESC_AMY;
        descriptor = new EditTaskDescriptorBuilder().withFrequency(VALID_FREQUENCY_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditTaskDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        //priority
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + PRIORITY_DESC_AMY + TAG_DESC_FRIEND + PRIORITY_DESC_AMY
                + TAG_DESC_FRIEND + PRIORITY_DESC_BOB + TAG_DESC_HUSBAND;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        //garbage priority
        targetIndex = INDEX_FIRST_TASK;
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_AMY + PRIORITY_DESC_BOB;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        //frequency
        targetIndex = INDEX_FIRST_TASK;
        userInput = targetIndex.getOneBased() + FREQUENCY_DESC_AMY + TAG_DESC_FRIEND + FREQUENCY_DESC_AMY
                + TAG_DESC_FRIEND + FREQUENCY_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_multipleRepeatedTags_success() {
        //Repeated tags are supposed to be OK.
        Index targetIndex = INDEX_FIRST_TASK;
        String userInput = targetIndex.getOneBased() + TAG_DESC_HUSBAND + TAG_DESC_FRIEND;
        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder()
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        //Repeated tags are supposed to be ok even with other non-repeated, valid inputs.
        targetIndex = INDEX_FIRST_TASK;
        userInput = targetIndex.getOneBased() + PRIORITY_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND;
        descriptor = new EditTaskDescriptorBuilder()
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                .withPriority(VALID_PRIORITY_BOB).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_TASK;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCommand.EditTaskDescriptor descriptor = new EditTaskDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
