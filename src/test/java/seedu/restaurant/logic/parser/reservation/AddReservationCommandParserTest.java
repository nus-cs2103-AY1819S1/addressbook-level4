package seedu.restaurant.logic.parser.reservation;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_DATE_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_NAME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_PAX_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_RESERVATION_TIME_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.restaurant.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_DATE_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_DATE_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_NAME_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_NAME_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_PAX_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_PAX_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_TAG_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_TAG_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_TIME_DESC_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.RESERVATION_TIME_DESC_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_DATE_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_NAME_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_PAX_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_TAG_ANDREW;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_TAG_BILLY;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_RESERVATION_TIME_ANDREW;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.testutil.reservation.TypicalReservations.ANDREW;

import org.junit.Test;

import seedu.restaurant.logic.commands.reservation.AddReservationCommand;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.testutil.reservation.ReservationBuilder;

//@@author m4dkip
public class AddReservationCommandParserTest {
    private AddReservationCommandParser parser = new AddReservationCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reservation expectedReservation = new ReservationBuilder(ANDREW).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + RESERVATION_NAME_DESC_ANDREW
                        + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple names - last name accepted
        assertParseSuccess(parser,
                RESERVATION_NAME_DESC_BILLY + RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple pax - last pax accepted
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_BILLY
                        + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple date - last date accepted
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_BILLY + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple time - last time accepted
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_BILLY + RESERVATION_TIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));

        // multiple tags - all accepted
        Reservation expectedReservationMultipleTags = new ReservationBuilder(ANDREW)
                .withTags(VALID_RESERVATION_TAG_ANDREW, VALID_RESERVATION_TAG_BILLY).build();
        assertParseSuccess(parser,
                RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATE_DESC_ANDREW
                        + RESERVATION_TIME_DESC_ANDREW + RESERVATION_TAG_DESC_ANDREW + RESERVATION_TAG_DESC_BILLY,
                new AddReservationCommand(expectedReservationMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Reservation expectedReservation = new ReservationBuilder(ANDREW).withTags().build();
        assertParseSuccess(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                new AddReservationCommand(expectedReservation));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReservationCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_RESERVATION_NAME_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW, expectedMessage);

        // missing pax prefix
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + VALID_RESERVATION_PAX_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + VALID_RESERVATION_DATE_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                expectedMessage);

        // missing time prefix
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + VALID_RESERVATION_TIME_ANDREW,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_RESERVATION_NAME_ANDREW + VALID_RESERVATION_PAX_ANDREW
                        + VALID_RESERVATION_DATE_ANDREW + VALID_RESERVATION_TIME_ANDREW, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_RESERVATION_NAME_DESC + RESERVATION_PAX_DESC_ANDREW
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid pax
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + INVALID_RESERVATION_PAX_DESC
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW, Pax.MESSAGE_PAX_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                + INVALID_RESERVATION_DATE_DESC + RESERVATION_TIME_DESC_ANDREW, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                + RESERVATION_DATE_DESC_ANDREW + INVALID_RESERVATION_TIME_DESC, Time.MESSAGE_TIME_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, RESERVATION_NAME_DESC_ANDREW + RESERVATION_PAX_DESC_ANDREW
                + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW
                + INVALID_TAG_DESC, Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_RESERVATION_NAME_DESC + INVALID_RESERVATION_PAX_DESC
                        + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW, Name.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + RESERVATION_NAME_DESC_ANDREW
                        + RESERVATION_PAX_DESC_ANDREW + RESERVATION_DATE_DESC_ANDREW + RESERVATION_TIME_DESC_ANDREW,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReservationCommand.MESSAGE_USAGE));
    }
}
