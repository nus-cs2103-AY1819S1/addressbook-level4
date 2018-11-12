package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewEmailCommand;
import seedu.address.model.email.Subject;

//@@author EatOrBeEaten
public class ViewEmailCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewEmailCommand.MESSAGE_USAGE);

    private ViewEmailCommandParser parser = new ViewEmailCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {

        // no subject specified
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsViewEmailCommand() {
        assertParseSuccess(parser, "Meeting", new ViewEmailCommand(new Subject("Meeting")));
    }
}
