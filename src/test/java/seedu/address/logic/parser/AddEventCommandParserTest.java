package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_DOCTORAPPT;

import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.testutil.ScheduledEventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();
    private final Event defaultEvent = new ScheduledEventBuilder().build();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new ScheduledEventBuilder(DOCTORAPPT).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent));

        // multiple names - only the last name accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MEETING + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent));

        // multiple event descriptions - only the last description accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_MEETING + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent));

        // multiple dates - last date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_MEETING + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent));

        // multiple times - last time accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_MEETING
                        + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_MEETING + EVENT_ADDRESS_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing event name prefix
        assertParseFailure(parser,
                VALID_EVENT_NAME_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event description prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + VALID_EVENT_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event date prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + VALID_EVENT_DATE_DOCTORAPPT
                        + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event time prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + VALID_EVENT_TIME_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event address prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_TIME_DESC_DOCTORAPPT + VALID_EVENT_ADDRESS_DOCTORAPPT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_EVENT_NAME_DOCTORAPPT + VALID_EVENT_DESC_DOCTORAPPT + VALID_EVENT_DATE_DOCTORAPPT
                        + VALID_EVENT_TIME_DOCTORAPPT + VALID_EVENT_ADDRESS_DOCTORAPPT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_DESC_DESC_DOCTORAPPT
                + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                EventName.MESSAGE_NAME_CONSTRAINTS);

        // invalid event description
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + INVALID_EVENT_DESC_DESC
                + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                EventDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + INVALID_EVENT_DATE_DESC + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                EventDate.MESSAGE_DATE_CONSTRAINTS);

        // invalid time
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + INVALID_EVENT_TIME_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT,
                EventTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT + INVALID_EVENT_ADDRESS_DESC,
                EventAddress.MESSAGE_ADDRESS_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT + INVALID_EVENT_ADDRESS_DESC,
                EventName.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
