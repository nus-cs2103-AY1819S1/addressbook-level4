package seedu.superta.logic.parser;

import static seedu.superta.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_GENERAL_TUTORIAL_GROUP_ID;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_SESSION_NAME;
import static seedu.superta.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.superta.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.superta.logic.commands.ViewSessionCommand;

// @@author Caephler
public class ViewSessionCommandParserTest {

    private ViewSessionCommandParser parser = new ViewSessionCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertCommandUsageFailure("         ");
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        // wrong prefix
        assertCommandUsageFailure(" " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "1 "
            + PREFIX_STUDENT_ID + "A01");

        // Not enough prefixes supplied
        assertCommandUsageFailure(" " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "1");
    }

    @Test
    public void parse_vaildArgs_returnsCommand() {
        ViewSessionCommand expectedCommand = new ViewSessionCommand("04a", "Lab1");

        assertParseSuccess(parser, " " + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a "
            + PREFIX_SESSION_NAME + "Lab1", expectedCommand);

        // reverse order should also be ok
        assertParseSuccess(parser, " " + PREFIX_SESSION_NAME + "Lab1 "
                + PREFIX_GENERAL_TUTORIAL_GROUP_ID + "04a", expectedCommand);

    }


    private void assertCommandUsageFailure(String input) {
        assertParseFailure(parser, input,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewSessionCommand.MESSAGE_USAGE));
    }
}
