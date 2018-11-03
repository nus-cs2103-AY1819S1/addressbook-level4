package seedu.scheduler.logic.parser;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.END_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.EVENT_NAME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_START_END_DATETIMES;
import static seedu.scheduler.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.scheduler.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.scheduler.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_TYPE_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_TYPE_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_UNTIL_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.REPEAT_UNTIL_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.START_DATETIME_DESC_MA3220;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.TAG_DESC_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_PLAY;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_SCHOOL;
import static seedu.scheduler.logic.commands.CommandTestUtil.VENUE_DESC_MA2101;
import static seedu.scheduler.logic.commands.CommandTestUtil.VENUE_DESC_MA3220;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.scheduler.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.scheduler.testutil.TypicalEvents.MA2101_JANUARY_1_2018_YEARLY;
import static seedu.scheduler.testutil.TypicalEvents.MA3220_JANUARY_1_2019_SINGLE;

import org.junit.Test;

import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.model.event.Event;
import seedu.scheduler.model.event.EventName;
import seedu.scheduler.model.tag.Tag;
import seedu.scheduler.testutil.EventBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(MA2101_JANUARY_1_2018_YEARLY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101
                + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101
                + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple names - last event name accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA3220 + EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101
                        + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple start date time - last start date time accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA3220 + START_DATETIME_DESC_MA2101
                        + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple end date time - last end date time accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA3220
                        + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple description - last description accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101
                        + DESCRIPTION_DESC_MA3220 + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple venue - last venue accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101
                        + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA3220 + VENUE_DESC_MA2101
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple repeat type - last repeat type accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101
                        + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA3220
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple repeat until date time - last repeat until date time accepted
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101
                        + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101
                        + REPEAT_UNTIL_DATETIME_DESC_MA3220 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL,
                new AddCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(MA2101_JANUARY_1_2018_YEARLY)
                .withTags(VALID_TAG_SCHOOL, VALID_TAG_PLAY).build();
        assertParseSuccess(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101
                        + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101
                        + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_PLAY + TAG_DESC_SCHOOL,
                new AddCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        Event expectedEvent = new EventBuilder(MA3220_JANUARY_1_2019_SINGLE).withVenue("").build();
        assertParseSuccess(parser, EVENT_NAME_DESC_MA3220 + START_DATETIME_DESC_MA3220
                        + END_DATETIME_DESC_MA3220 + DESCRIPTION_DESC_MA3220
                        + REPEAT_TYPE_DESC_MA3220 + REPEAT_UNTIL_DATETIME_DESC_MA3220 + TAG_DESC_PLAY,
                new AddCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing event name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_MA2101 + START_DATETIME_DESC_MA2101
                        + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101 + VENUE_DESC_MA2101
                        + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_PLAY,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid event name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + START_DATETIME_DESC_MA2101
                + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                + TAG_DESC_PLAY + TAG_DESC_SCHOOL, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, EVENT_NAME_DESC_MA2101 + START_DATETIME_DESC_MA2101
                + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                + INVALID_TAG_DESC + VALID_TAG_SCHOOL, Tag.MESSAGE_CONSTRAINTS);

        // end date time before start date time
        assertParseFailure(parser, EVENT_NAME_DESC_MA2101 + INVALID_START_END_DATETIMES[0]
                + INVALID_START_END_DATETIMES[1] + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                + TAG_DESC_PLAY + VALID_TAG_SCHOOL, Event.MESSAGE_DATETIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + START_DATETIME_DESC_MA2101
                + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101 + REPEAT_UNTIL_DATETIME_DESC_MA2101
                + INVALID_TAG_DESC + VALID_TAG_SCHOOL, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_MA2101
                        + START_DATETIME_DESC_MA2101 + END_DATETIME_DESC_MA2101 + DESCRIPTION_DESC_MA2101
                        + VENUE_DESC_MA2101 + REPEAT_TYPE_DESC_MA2101
                        + REPEAT_UNTIL_DATETIME_DESC_MA2101 + TAG_DESC_SCHOOL + TAG_DESC_PLAY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
