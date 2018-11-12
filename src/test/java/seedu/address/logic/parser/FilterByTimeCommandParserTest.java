package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FilterByTimeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Time;

public class FilterByTimeCommandParserTest {
    private FilterByTimeCommandParser parser = new FilterByTimeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "  ", String.format(
            MESSAGE_INVALID_COMMAND_FORMAT, FilterByTimeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindCommand() throws ParseException {
        // no leading and trailing whitespaces
        FilterByTimeCommand expectedFindCommand =
                new FilterByTimeCommand(new Time("mon 1300 1500"));
        assertParseFailure(parser, "ts/mon 1300 1500", "Invalid command format! \n" +
                "filterByTime: filter by tutorial time slot. ts/TIME ");


    }
}
