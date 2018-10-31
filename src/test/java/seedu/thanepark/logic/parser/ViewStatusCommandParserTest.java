package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.thanepark.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;
import seedu.thanepark.logic.commands.ViewStatusCommand;
import seedu.thanepark.model.ride.RideStatusPredicate;
import seedu.thanepark.model.ride.Status;

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
