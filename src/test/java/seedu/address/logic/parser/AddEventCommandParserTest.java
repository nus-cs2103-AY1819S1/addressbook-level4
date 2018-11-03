package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_CONTACT_INDEX_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_CONTACT_INDEX_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TAG_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TAG_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_CONTACT_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_TOO_EARLY_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_TOO_EARLY_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_ADDRESS_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CONTACT_INDEX_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_CONTACT_INDEX_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_MEETING;
import static seedu.address.logic.parser.AddEventCommandParser.MESSAGE_INVALID_START_END_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalEvents.DOCTORAPPT;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventAddress;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ScheduledEventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();
    private final Event defaultEvent = new ScheduledEventBuilder().build();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventTags(VALID_TAG_APPOINTMENT)
                .withEventContacts().build();
        Set<Index> expectedIndices = new HashSet<>();
        expectedIndices.add(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple names - only the last name accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MEETING + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple event descriptions - only the last description accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_MEETING + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple dates - last date accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_MEETING + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple times - last start time accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_MEETING
                        + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple times - last end time accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_MEETING + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_MEETING + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple indices - all accepted
        expectedIndices.add(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_2)));
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_MEETING + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_CONTACT_INDEX_DESC_MEETING
                        + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventTags(VALID_TAG_APPOINTMENT, VALID_TAG_MEETING)
                .withEventContacts() // event is not created with any contacts in parser
                .build();
        assertParseSuccess(parser, EVENT_NAME_DESC_DOCTORAPPT
                + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_MEETING + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT + EVENT_CONTACT_INDEX_DESC_MEETING
                        + EVENT_TAG_DESC_DOCTORAPPT + EVENT_TAG_DESC_MEETING,
                new AddEventCommand(expectedEventMultipleTags, expectedIndices));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero indices
        Event expectedEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventTags(VALID_TAG_APPOINTMENT)
                .withEventContacts()
                .build();
        assertParseSuccess(parser, EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT + EVENT_TAG_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, new HashSet<>()));

        // zero event tags
        expectedEvent = new ScheduledEventBuilder(DOCTORAPPT)
                .withEventTags()
                .withEventContacts()
                .build();

        Set<Index> expectedIndices = new HashSet<>();
        expectedIndices.add(Index.fromOneBased(Integer.parseInt(VALID_EVENT_CONTACT_INDEX_1)));

        assertParseSuccess(parser, EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT,
                new AddEventCommand(expectedEvent, expectedIndices));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing event name prefix
        assertParseFailure(parser,
                VALID_EVENT_NAME_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event description prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + VALID_EVENT_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event date prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + VALID_EVENT_DATE_DOCTORAPPT
                        + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event time prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + VALID_EVENT_START_TIME_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT,
                expectedMessage);

        // missing event address prefix
        assertParseFailure(parser,
                EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT + EVENT_DATE_DESC_DOCTORAPPT
                        + EVENT_START_TIME_DESC_DOCTORAPPT + VALID_EVENT_ADDRESS_DOCTORAPPT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                VALID_EVENT_NAME_DOCTORAPPT + VALID_EVENT_DESC_DOCTORAPPT + VALID_EVENT_DATE_DOCTORAPPT
                        + VALID_EVENT_START_TIME_DOCTORAPPT + VALID_EVENT_ADDRESS_DOCTORAPPT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_DESC_DESC_DOCTORAPPT
                + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT + EVENT_CONTACT_INDEX_DESC_MEETING,
                EventName.MESSAGE_NAME_CONSTRAINTS);

        // invalid event description
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + INVALID_EVENT_DESC_DESC
                + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT + EVENT_CONTACT_INDEX_DESC_MEETING,
                EventDescription.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + INVALID_EVENT_DATE_DESC + EVENT_START_TIME_DESC_DOCTORAPPT + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT + EVENT_CONTACT_INDEX_DESC_MEETING,
                EventDate.MESSAGE_DATE_CONSTRAINTS);

        // invalid start time (format-wise)
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + INVALID_EVENT_START_TIME_DESC + EVENT_END_TIME_DESC_DOCTORAPPT
                        + EVENT_ADDRESS_DESC_DOCTORAPPT + EVENT_CONTACT_INDEX_DESC_MEETING,
                EventTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time (format-wise)
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + INVALID_EVENT_END_TIME_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_MEETING,
                EventTime.MESSAGE_TIME_CONSTRAINTS);

        // invalid end time (end time earlier than start time)
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + INVALID_EVENT_END_TIME_TOO_EARLY_DESC + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_MEETING,
                String.format(MESSAGE_INVALID_START_END_TIME, VALID_EVENT_START_TIME_DOCTORAPPT,
                        INVALID_EVENT_END_TIME_TOO_EARLY_DOCTORAPPT));

        // invalid address
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + INVALID_EVENT_ADDRESS_DESC
                        + EVENT_CONTACT_INDEX_DESC_MEETING,
                EventAddress.MESSAGE_ADDRESS_CONSTRAINTS);

        // invalid contact index
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + INVALID_EVENT_CONTACT_INDEX,
                MESSAGE_INVALID_INDEX);

        // invalid event tag
        assertParseFailure(parser, EVENT_NAME_DESC_DOCTORAPPT + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_MEETING + INVALID_TAG_DESC + VALID_TAG_APPOINTMENT,
                Tag.MESSAGE_TAG_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_DESC_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + INVALID_EVENT_ADDRESS_DESC,
                EventName.MESSAGE_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_DOCTORAPPT
                        + EVENT_DATE_DESC_DOCTORAPPT + EVENT_START_TIME_DESC_DOCTORAPPT
                        + EVENT_END_TIME_DESC_DOCTORAPPT + EVENT_ADDRESS_DESC_DOCTORAPPT
                        + EVENT_CONTACT_INDEX_DESC_MEETING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }
}
