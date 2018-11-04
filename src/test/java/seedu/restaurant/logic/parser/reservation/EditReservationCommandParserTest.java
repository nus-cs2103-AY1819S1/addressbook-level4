package seedu.restaurant.logic.parser.reservation;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_DATETIME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_NAME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_PAX_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_DATETIME_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_DATETIME_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_NAME_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_PAX_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_PAX_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.restaurant.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_DATETIME_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_DATETIME_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_NAME_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_TAG;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_THIRD;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.reservation.EditReservationCommand;
import seedu.restaurant.logic.commands.reservation.EditReservationCommand.EditReservationDescriptor;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.reservation.EditReservationDescriptorBuilder;

//@@author m4dkip
public class EditReservationCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReservationCommand.MESSAGE_USAGE);

    private EditReservationCommandParser parser = new EditReservationCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_RESERVATION_NAME_ANDREW, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditReservationCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + RESERVATION_NAME_DESC_ANDREW, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + RESERVATION_NAME_DESC_ANDREW, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1" + INVALID_RESERVATION_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
        // invalid pax
        assertParseFailure(parser, "1" + INVALID_RESERVATION_PAX_DESC, Pax.MESSAGE_PAX_CONSTRAINTS);
        // invalid dateTime
        assertParseFailure(parser, "1" + INVALID_RESERVATION_DATETIME_DESC,
                "DateTime value should be in the form 2018-12-31T10:00:00");

        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS); // invalid tag

        // invalid pax followed by valid dateTime
        assertParseFailure(parser, "1" + INVALID_RESERVATION_PAX_DESC + RESERVATION_DATETIME_DESC_ANDREW,
                Pax.MESSAGE_PAX_CONSTRAINTS);

        // valid pax followed by invalid pax. The test case for invalid pax followed by valid pax
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + RESERVATION_PAX_DESC_BILLY + INVALID_RESERVATION_PAX_DESC,
                Pax.MESSAGE_PAX_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Reservation} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_TAG_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1" + INVALID_RESERVATION_NAME_DESC + INVALID_RESERVATION_PAX_DESC
                        + RESERVATION_DATETIME_DESC_ANDREW, Name.MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND;
        String userInput = targetIndex.getOneBased() + RESERVATION_PAX_DESC_BILLY + TAG_DESC_HUSBAND
                + RESERVATION_DATETIME_DESC_ANDREW + RESERVATION_NAME_DESC_ANDREW + TAG_DESC_FRIEND;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_RESERVATION_NAME_ANDREW).withPax(VALID_RESERVATION_PAX_BILLY)
                .withDateTime(VALID_RESERVATION_DATETIME_ANDREW).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditReservationCommand expectedCommand = new EditReservationCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + RESERVATION_PAX_DESC_BILLY + RESERVATION_DATETIME_DESC_ANDREW;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withPax(VALID_RESERVATION_PAX_BILLY).withDateTime(VALID_RESERVATION_DATETIME_ANDREW).build();
        EditReservationCommand expectedCommand = new EditReservationCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + RESERVATION_NAME_DESC_ANDREW;
        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withName(VALID_RESERVATION_NAME_ANDREW).build();
        EditReservationCommand expectedCommand = new EditReservationCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // pax
        userInput = targetIndex.getOneBased() + RESERVATION_PAX_DESC_ANDREW;
        descriptor = new EditReservationDescriptorBuilder().withPax(VALID_RESERVATION_PAX_ANDREW).build();
        expectedCommand = new EditReservationCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // dateTime
        userInput = targetIndex.getOneBased() + RESERVATION_DATETIME_DESC_ANDREW;
        descriptor = new EditReservationDescriptorBuilder().withDateTime(VALID_RESERVATION_DATETIME_ANDREW).build();
        expectedCommand = new EditReservationCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditReservationDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditReservationCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATETIME_DESC_ANDREW
                + TAG_DESC_FRIEND + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATETIME_DESC_ANDREW + TAG_DESC_FRIEND
                + RESERVATION_PAX_DESC_BILLY + RESERVATION_DATETIME_DESC_BILLY + TAG_DESC_HUSBAND;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder()
                .withPax(VALID_RESERVATION_PAX_BILLY).withDateTime(VALID_RESERVATION_DATETIME_BILLY)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        EditReservationCommand expectedCommand = new EditReservationCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + INVALID_RESERVATION_PAX_DESC + RESERVATION_PAX_DESC_BILLY;
        EditReservationDescriptor descriptor =
                new EditReservationDescriptorBuilder().withPax(VALID_RESERVATION_PAX_BILLY).build();
        EditReservationCommand expectedCommand = new EditReservationCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + RESERVATION_DATETIME_DESC_BILLY + INVALID_RESERVATION_PAX_DESC
                + RESERVATION_PAX_DESC_BILLY;
        descriptor = new EditReservationDescriptorBuilder().withPax(VALID_RESERVATION_PAX_BILLY)
                .withDateTime(VALID_RESERVATION_DATETIME_BILLY).build();
        expectedCommand = new EditReservationCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditReservationDescriptor descriptor = new EditReservationDescriptorBuilder().withTags().build();
        EditReservationCommand expectedCommand = new EditReservationCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
