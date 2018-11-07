package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.model.event.EventDate;

public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + " " + PREFIX_INDEX + "1",
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_DATE_DESC_DOCTORAPPT + " " + PREFIX_INDEX + "1",
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT));

        // multiple dates - last date accepted
        assertParseSuccess(parser, EVENT_DATE_DESC_MEETING + EVENT_DATE_DESC_DOCTORAPPT + " " + PREFIX_INDEX + "1",
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT));

        // multiple indices - last index accepted
        assertParseSuccess(parser, EVENT_DATE_DESC_DOCTORAPPT + " " + PREFIX_INDEX + "2" + " " + PREFIX_INDEX + "1",
                new DeleteEventCommand(new EventDate(VALID_EVENT_DATE_DOCTORAPPT), INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

        // missing date prefix
        assertParseFailure(parser, VALID_EVENT_DATE_DOCTORAPPT + " " + PREFIX_INDEX + "1",
                expectedMessage);

        // missing index prefix
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + " " + "1",
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_DATE_DOCTORAPPT + " " + "1",
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid date
        assertParseFailure(parser, INVALID_EVENT_DATE_DESC + " " + PREFIX_INDEX + "1",
                EventDate.MESSAGE_DATE_CONSTRAINTS);

        // invalid index
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + " " + PREFIX_INDEX + "-1",
                MESSAGE_INVALID_INDEX);


        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_DATE_DESC + " " + PREFIX_INDEX + "-1",
                EventDate.MESSAGE_DATE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_DATE_DESC_DOCTORAPPT + " " + PREFIX_INDEX + "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE));
    }
}
