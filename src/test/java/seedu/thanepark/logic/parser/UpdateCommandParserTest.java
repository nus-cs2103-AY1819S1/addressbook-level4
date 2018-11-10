package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_MAINTENANCE_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_WAIT_TIME_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.INVALID_ZONE_DESC;
import static seedu.thanepark.logic.commands.CommandTestUtil.MAINTENANCE_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.MAINTENANCE_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_JESSIE;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_SYMBOLS;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.WAIT_TIME_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.WAIT_TIME_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.ZONE_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.ZONE_DESC_BOB;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.thanepark.commons.core.index.Index;
import seedu.thanepark.logic.commands.UpdateCommand;
import seedu.thanepark.logic.commands.UpdateCommand.UpdateRideDescriptor;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;
import seedu.thanepark.testutil.UpdateRideDescriptorBuilder;

public class UpdateCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", UpdateCommand.MESSAGE_NOT_UPDATED);

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
        // invalid name
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid maintenance
        assertParseFailure(parser, "1" + INVALID_MAINTENANCE_DESC,
                Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);
        // invalid email
        assertParseFailure(parser, "1" + INVALID_WAIT_TIME_DESC, WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);
        // invalid thanepark
        assertParseFailure(parser, "1" + INVALID_ZONE_DESC, Zone.MESSAGE_ZONE_CONSTRAINTS);
        // invalid tag
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // invalid maintenance followed by valid email
        assertParseFailure(parser, "1" + INVALID_MAINTENANCE_DESC + WAIT_TIME_DESC_AMY,
                Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);

        // valid maintenance followed by invalid maintenance. The test case for invalid maintenance followed by
        // valid maintenance is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + MAINTENANCE_DESC_BOB + INVALID_MAINTENANCE_DESC,
                Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Ride} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND,
                Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + VALID_NAME_SYMBOLS + INVALID_WAIT_TIME_DESC + INVALID_ZONE_DESC
                        + VALID_MAINTENANCE_AMY,
                WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_RIDE;
        String userInput = targetIndex.getOneBased() + MAINTENANCE_DESC_BOB + TAG_DESC_HUSBAND
                + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withName(VALID_NAME_AMY)
                .withMaintenance(VALID_MAINTENANCE_BOB).withWaitTime(VALID_WAIT_TIME_AMY).withAddress(VALID_ZONE_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_AMY;

        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withMaintenance(VALID_MAINTENANCE_BOB)
                .withWaitTime(VALID_WAIT_TIME_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withName(VALID_NAME_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // name with symbols
        targetIndex = INDEX_FIRST_PERSON;
        userInput = targetIndex.getOneBased() + VALID_NAME_SYMBOLS;
        descriptor = new UpdateRideDescriptorBuilder().withName(VALID_NAME_JESSIE).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // maintenance
        userInput = targetIndex.getOneBased() + MAINTENANCE_DESC_AMY;
        descriptor = new UpdateRideDescriptorBuilder().withMaintenance(VALID_MAINTENANCE_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + WAIT_TIME_DESC_AMY;
        descriptor = new UpdateRideDescriptorBuilder().withWaitTime(VALID_WAIT_TIME_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // thanepark
        userInput = targetIndex.getOneBased() + ZONE_DESC_AMY;
        descriptor = new UpdateRideDescriptorBuilder().withAddress(VALID_ZONE_AMY).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new UpdateRideDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + MAINTENANCE_DESC_AMY + ZONE_DESC_AMY + WAIT_TIME_DESC_AMY
                + TAG_DESC_FRIEND + MAINTENANCE_DESC_AMY + ZONE_DESC_AMY + WAIT_TIME_DESC_AMY + TAG_DESC_FRIEND
                + MAINTENANCE_DESC_BOB + ZONE_DESC_BOB + WAIT_TIME_DESC_BOB + TAG_DESC_HUSBAND;

        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withMaintenance(VALID_MAINTENANCE_BOB)
                .withWaitTime(VALID_WAIT_TIME_BOB)
                .withAddress(VALID_ZONE_BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_MAINTENANCE_DESC + MAINTENANCE_DESC_BOB;
        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder()
                .withMaintenance(VALID_MAINTENANCE_BOB).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + WAIT_TIME_DESC_BOB + INVALID_MAINTENANCE_DESC + ZONE_DESC_BOB
                + MAINTENANCE_DESC_BOB;
        descriptor = new UpdateRideDescriptorBuilder()
                .withMaintenance(VALID_MAINTENANCE_BOB).withWaitTime(VALID_WAIT_TIME_BOB)
                .withAddress(VALID_ZONE_BOB).build();
        expectedCommand = new UpdateCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        UpdateRideDescriptor descriptor = new UpdateRideDescriptorBuilder().withTags().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
