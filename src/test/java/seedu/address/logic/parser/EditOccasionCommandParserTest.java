package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONDATE_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONLOCATION_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_OCCASIONNAME_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONDATE_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONDATE_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONLOCATION_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONLOCATION_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.OCCASIONNAME_DESC_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.TAG_DESC_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.TAG_DESC_STUDY;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONDATE_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONLOCATION_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_ONE;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_OCCASIONNAME_TWO;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_SLEEP;
import static seedu.address.logic.commands.CommandOccasionTestUtil.VALID_TAG_STUDY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OCCASION;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_OCCASION;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditOccasionCommand;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.OccasionDescriptorBuilder;

public class EditOccasionCommandParserTest {
    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditOccasionCommand.MESSAGE_USAGE);

    private EditOccasionCommandParser parser = new EditOccasionCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_OCCASIONNAME_ONE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditOccasionCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + OCCASIONNAME_DESC_ONE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + OCCASIONNAME_DESC_TWO, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid occasionName
        assertParseFailure(parser, "1" + INVALID_OCCASIONNAME_DESC, OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);
        // invalid occasionDate
        assertParseFailure(parser, "1" + INVALID_OCCASIONDATE_DESC, OccasionDate.MESSAGE_OCCASIONDATE_CONSTRAINTS);
        // invalid occasionLocation
        assertParseFailure(parser, "1" + INVALID_OCCASIONLOCATION_DESC,
                OccasionLocation.MESSAGE_OCCASIONLOCATION_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid occasionName followed by valid occasionDate
        assertParseFailure(parser, "1" + INVALID_OCCASIONNAME_DESC + OCCASIONLOCATION_DESC_ONE,
                OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);

        // valid occasionName followed by invalid occasionName. The test case for invalid occasionName
        // followed by valid occasionName is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + OCCASIONNAME_DESC_ONE + INVALID_OCCASIONNAME_DESC,
                OccasionName.MESSAGE_OCCASIONNAME_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Occasion} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_STUDY + TAG_DESC_SLEEP + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_STUDY + TAG_EMPTY + TAG_DESC_SLEEP, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_STUDY + TAG_DESC_SLEEP, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_OCCASIONDATE_DESC + INVALID_TAG_DESC
                        + INVALID_OCCASIONLOCATION_DESC, OccasionDate.MESSAGE_OCCASIONDATE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_OCCASION;
        String userInput = targetIndex.getOneBased() + TAG_DESC_STUDY
                + OCCASIONNAME_DESC_TWO + OCCASIONDATE_DESC_TWO + OCCASIONLOCATION_DESC_TWO;

        OccasionDescriptor descriptor = new OccasionDescriptorBuilder()
                .withOccasionName(VALID_OCCASIONNAME_TWO)
                .withOccasionDate(VALID_OCCASIONDATE_TWO).withOccasionLocation(VALID_OCCASIONLOCATION_TWO)
                .withTags(VALID_TAG_STUDY).build();
        EditOccasionCommand expectedCommand = new EditOccasionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_OCCASION;
        String userInput = targetIndex.getOneBased() + OCCASIONNAME_DESC_ONE + OCCASIONLOCATION_DESC_ONE;

        OccasionDescriptor descriptor = new OccasionDescriptorBuilder()
                .withOccasionName(VALID_OCCASIONNAME_ONE)
                .withOccasionLocation(VALID_OCCASIONLOCATION_ONE).build();
        EditOccasionCommand expectedCommand = new EditOccasionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // occasionName
        Index targetIndex = INDEX_THIRD_OCCASION;
        String userInput = targetIndex.getOneBased() + OCCASIONNAME_DESC_TWO;
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder()
                .withOccasionName(VALID_OCCASIONNAME_TWO).build();
        EditOccasionCommand expectedCommand = new EditOccasionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // occasionDate
        userInput = targetIndex.getOneBased() + OCCASIONDATE_DESC_ONE;
        descriptor = new OccasionDescriptorBuilder().withOccasionDate(VALID_OCCASIONDATE_ONE).build();
        expectedCommand = new EditOccasionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // occasionLocation
        userInput = targetIndex.getOneBased() + OCCASIONLOCATION_DESC_TWO;
        descriptor = new OccasionDescriptorBuilder().withOccasionLocation(VALID_OCCASIONLOCATION_TWO).build();
        expectedCommand = new EditOccasionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_SLEEP;
        descriptor = new OccasionDescriptorBuilder().withTags(VALID_TAG_SLEEP).build();
        expectedCommand = new EditOccasionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_OCCASION;
        String userInput = targetIndex.getOneBased() + OCCASIONNAME_DESC_ONE + OCCASIONLOCATION_DESC_ONE
                + OCCASIONDATE_DESC_ONE + TAG_DESC_SLEEP + OCCASIONNAME_DESC_ONE + OCCASIONLOCATION_DESC_ONE
                + OCCASIONDATE_DESC_ONE + TAG_DESC_SLEEP + OCCASIONNAME_DESC_TWO + OCCASIONLOCATION_DESC_TWO
                + OCCASIONDATE_DESC_TWO + TAG_DESC_STUDY;

        OccasionDescriptor descriptor = new OccasionDescriptorBuilder().withOccasionName(VALID_OCCASIONNAME_TWO)
                .withOccasionLocation(VALID_OCCASIONLOCATION_TWO).withOccasionDate(VALID_OCCASIONDATE_TWO)
                .withTags(VALID_TAG_SLEEP, VALID_TAG_STUDY).build();
        EditOccasionCommand expectedCommand = new EditOccasionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_OCCASION;
        String userInput = targetIndex.getOneBased() + INVALID_OCCASIONDATE_DESC + OCCASIONDATE_DESC_TWO;
        OccasionDescriptor descriptor = new OccasionDescriptorBuilder()
                .withOccasionDate(VALID_OCCASIONDATE_TWO).build();
        EditOccasionCommand expectedCommand = new EditOccasionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + OCCASIONLOCATION_DESC_ONE + INVALID_OCCASIONDATE_DESC
                + OCCASIONDATE_DESC_ONE;
        descriptor = new OccasionDescriptorBuilder().withOccasionLocation(VALID_OCCASIONLOCATION_ONE)
                .withOccasionDate(VALID_OCCASIONDATE_ONE).build();
        expectedCommand = new EditOccasionCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_OCCASION;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        OccasionDescriptor descriptor = new OccasionDescriptorBuilder().withTags().build();
        EditOccasionCommand expectedCommand = new EditOccasionCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
