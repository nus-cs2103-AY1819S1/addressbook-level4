package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewStatusCommand;
import seedu.address.model.ride.RideStatusPredicate;
import seedu.address.model.ride.Status;

public class ViewStatusCommandParserTest {

    private ViewStatusCommandParser parser = new ViewStatusCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleArgs_throwsParseException() {
        assertParseFailure(parser, "open shutdown",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "close",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatusCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnsViewStatusCommand() {
        ViewStatusCommand expectedCommand = new ViewStatusCommand(new RideStatusPredicate(Status.OPEN));

        // no leading or trailing whitespaces
        assertParseSuccess(parser, "open", expectedCommand);

        // contains whitespaces
        assertParseSuccess(parser, " \n open \t ", expectedCommand);

        // mixed-case
        assertParseSuccess(parser, "OpEn", expectedCommand);
    }
}
