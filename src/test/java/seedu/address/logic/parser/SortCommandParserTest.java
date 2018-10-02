package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.PersonPropertyComparator;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "name phone",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "phone address",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "something else",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

    }


    @Test
    public void parse_validArgs_returnsSortCommand() {
        // no leading and trailing whitespaces, lower case
        for (PersonPropertyComparator personPropertyComparator : PersonPropertyComparator.values()) {
            SortCommand expectedSortCommand = new SortCommand(personPropertyComparator);
            String property = personPropertyComparator.toString();
            assertParseSuccess(parser, property, expectedSortCommand);
        }

        // multiple whitespaces between keywords
        for (PersonPropertyComparator personPropertyComparator : PersonPropertyComparator.values()) {
            SortCommand expectedSortCommand = new SortCommand(personPropertyComparator);
            String property = personPropertyComparator.toString();
            assertParseSuccess(parser, "\n \n \t " + property + "\t", expectedSortCommand);
        }

        // Upper Case
        for (PersonPropertyComparator personPropertyComparator : PersonPropertyComparator.values()) {
            SortCommand expectedSortCommand = new SortCommand(personPropertyComparator);
            String property = personPropertyComparator.toString();
            assertParseSuccess(parser, property.toUpperCase(), expectedSortCommand);
        }
    }
}
