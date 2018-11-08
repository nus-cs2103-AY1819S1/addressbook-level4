package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_FIRST_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_INVALID_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_SECOND_INDEX_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.logic.commands.EditEventAddressCommand;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;

public class EditEventAddressCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventAddressCommand.MESSAGE_USAGE);

    private EditEventAddressCommandParser parser = new EditEventAddressCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));

        // multiple dates - last date accepted
        assertParseSuccess(parser, EVENT_DATE_DESC_MEETING + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_FIRST_INDEX_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));

        // multiple indices - last index accepted
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_SECOND_INDEX_DESC
                        + EVENT_FIRST_INDEX_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + EVENT_ADDRESS_DESC_MEETING + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));
    }

    @Test
    public void parse_invalidAddressFollowedByValidAddress_success() {
        // no other valid addresses specified
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + INVALID_EVENT_ADDRESS_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));

        // other valid addresses specified
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + EVENT_ADDRESS_DESC_MEETING + INVALID_EVENT_ADDRESS_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new EditEventAddressCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT,
                        new EventAddress(VALID_EVENT_ADDRESS_DOCTORAPPT)));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing date prefix
        assertParseFailure(parser, VALID_EVENT_DATE_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + EVENT_ADDRESS_DESC_DOCTORAPPT, MESSAGE_INVALID_FORMAT);

        // missing index prefix
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + " " + "1"
                + EVENT_ADDRESS_DESC_DOCTORAPPT, MESSAGE_INVALID_FORMAT);

        // missing address prefix
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                + VALID_EVENT_ADDRESS_DOCTORAPPT, MESSAGE_INVALID_FORMAT);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_DATE_DOCTORAPPT + " " + "1"
                + VALID_EVENT_ADDRESS_DOCTORAPPT, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_EVENT_DATE_DESC + EVENT_FIRST_INDEX_DESC
                + EVENT_ADDRESS_DESC_DOCTORAPPT,
                EventDate.MESSAGE_DATE_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_INVALID_INDEX_DESC
                + EVENT_ADDRESS_DESC_DOCTORAPPT,
                MESSAGE_INVALID_INDEX);

        // invalid address
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                        + INVALID_EVENT_ADDRESS_DESC,
                EventAddress.MESSAGE_ADDRESS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_DATE_DESC + EVENT_INVALID_INDEX_DESC
                + EVENT_ADDRESS_DESC_DOCTORAPPT,
                EventDate.MESSAGE_DATE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_DATE_DESC_DOCTORAPPT + EVENT_FIRST_INDEX_DESC
                + EVENT_ADDRESS_DESC_DOCTORAPPT,
                MESSAGE_INVALID_FORMAT);
    }
}
