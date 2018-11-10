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
import static seedu.thanepark.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.thanepark.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.thanepark.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_JESSIE;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_NAME_SYMBOLS;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.VALID_ZONE_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.WAIT_TIME_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.WAIT_TIME_DESC_BOB;
import static seedu.thanepark.logic.commands.CommandTestUtil.ZONE_DESC_AMY;
import static seedu.thanepark.logic.commands.CommandTestUtil.ZONE_DESC_BOB;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.thanepark.testutil.TypicalRides.AMY;
import static seedu.thanepark.testutil.TypicalRides.BOB;

import org.junit.Test;

import seedu.thanepark.logic.commands.AddCommand;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;
import seedu.thanepark.testutil.RideBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Ride expectedRide = new RideBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB
                + ZONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRide));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB
                + ZONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRide));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MAINTENANCE_DESC_AMY + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB
                + ZONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRide));

        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_AMY + WAIT_TIME_DESC_BOB
                + ZONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRide));

        // multiple addresses - last thanepark accepted
        assertParseSuccess(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_AMY
                + ZONE_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedRide));

        // multiple tags - all accepted
        Ride expectedRideMultipleTags = new RideBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedRideMultipleTags));

        // valid name with symbols
        Ride expectedRideWithSymbols = new RideBuilder(BOB).withName(VALID_NAME_JESSIE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertParseSuccess(parser, VALID_NAME_SYMBOLS + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedRideWithSymbols));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Ride expectedRide = new RideBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + MAINTENANCE_DESC_AMY + WAIT_TIME_DESC_AMY + ZONE_DESC_AMY,
                new AddCommand(expectedRide));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_MAINTENANCE_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + VALID_WAIT_TIME_BOB + ZONE_DESC_BOB,
                expectedMessage);

        // missing thanepark prefix
        assertParseFailure(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + VALID_ZONE_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_MAINTENANCE_BOB + VALID_WAIT_TIME_BOB + VALID_ZONE_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_MAINTENANCE_DESC + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + INVALID_WAIT_TIME_DESC + ZONE_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);

        // invalid thanepark
        assertParseFailure(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + INVALID_ZONE_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Zone.MESSAGE_ZONE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB + ZONE_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, VALID_NAME_SYMBOLS + MAINTENANCE_DESC_BOB + INVALID_WAIT_TIME_DESC
                + INVALID_ZONE_DESC, WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + MAINTENANCE_DESC_BOB + WAIT_TIME_DESC_BOB
                + ZONE_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
