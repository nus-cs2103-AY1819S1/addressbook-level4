package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.address.logic.commands.LoadTemplateCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class LoadTemplateCommandParserTest {

    private LoadTemplateCommandParser parser = new LoadTemplateCommandParser();

    @Test
    public void parse_validArgs_returnsLoadTemplateCommand() {
        assertParseSuccess(parser, "template.txt", new LoadTemplateCommand(Paths.get("template.txt")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadTemplateCommand.MESSAGE_USAGE));
    }
}
