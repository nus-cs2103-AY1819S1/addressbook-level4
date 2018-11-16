package seedu.address.logic.parser;

import static junit.framework.TestCase.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.SaveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SaveCommandParserTest {

    private SaveCommandParser parser = new SaveCommandParser();
    private final String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE);

    @Test
    public void commandParseSuccessfully() throws ParseException {
        String fileName = "test.jpg";
        assertParseSuccess(parser, "save " + fileName, new SaveCommand(fileName));
        String fileName2 = "test.TIFF";
        assertParseSuccess(parser, "save " + fileName2, new SaveCommand(fileName2));
        Command command = new PiconsoParser().parseCommand("save " + fileName);
        assertTrue(command.equals(new SaveCommand(fileName)));
    }

    @Test
    public void commandParseUnsuccessfully() {
        String input = "save";
        assertParseFailure(parser, input, errorMessage);
        String input2 = "save test test";
        assertParseFailure(parser, input2, errorMessage);
        String input3 = "save test.jPg";
        assertParseFailure(parser, input3, errorMessage);
        String input4 = "save test.kk";
        assertParseFailure(parser, input4, errorMessage);
    }

}
