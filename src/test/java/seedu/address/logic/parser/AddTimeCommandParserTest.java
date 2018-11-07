package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.AddTimeCommand;
import seedu.address.model.person.Time;

public class AddTimeCommandParserTest {
    private AddTimeCommandParser parser = new AddTimeCommandParser();

    @Test
    public void parseValidIndexValidTime() {
        String userInput = "1 ts/mon 1300 1500";
        AddTimeCommand expectedCommand = new AddTimeCommand(INDEX_FIRST_PERSON, new Time("mon 1300 1500"));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseInvalidIndexValidTime() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTimeCommand.MESSAGE_USAGE);

        String userInput = "-1 ts/mon 1300 1500";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "0 ts/mon 1300 1500";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parseValidIndexInvalidTime() {
        String expectedMessage = Time.MESSAGE_TIME_CONSTRAINTS;

        String userInput = "1 ts/mon 130 150";
        assertParseFailure(parser, userInput, expectedMessage);

        userInput = "1 ts/mon 1 3";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
