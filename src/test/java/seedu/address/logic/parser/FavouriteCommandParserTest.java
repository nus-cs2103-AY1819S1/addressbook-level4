package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_CONTACT_INDEX_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_DOCTORAPPT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_CONTACT_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_DOCTORAPPT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import org.junit.Test;

import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.model.event.EventDate;

public class FavouriteCommandParserTest {

    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        EventDate expectedEventDate = new EventDate(VALID_EVENT_DATE_DOCTORAPPT);

        assertParseSuccess(parser, " d/2018-09-01 i/1",
                new FavouriteCommand(expectedEventDate,INDEX_FIRST_EVENT));
    }

    @Test
    public void parse_compulsoryFieldMissing_Failure() {

        // missing event index
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));

        // missing event date
        assertParseFailure(parser, EVENT_CONTACT_INDEX_DESC_DOCTORAPPT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_Failure() {

        // invalid event index
        assertParseFailure(parser, EVENT_DATE_DESC_DOCTORAPPT + INVALID_EVENT_CONTACT_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));

        // invalid event date
        assertParseFailure(parser, INVALID_EVENT_DATE_DESC + EVENT_CONTACT_INDEX_DESC_DOCTORAPPT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCommand.MESSAGE_USAGE));

    }
}