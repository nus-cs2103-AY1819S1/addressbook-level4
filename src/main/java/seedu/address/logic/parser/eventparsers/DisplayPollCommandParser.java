package seedu.address.logic.parser.eventparsers;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.eventcommands.DeleteEventCommand;
import seedu.address.logic.commands.eventcommands.DisplayPollCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses inputs arguments and creates a new DisplayPollCommand object.
 */
public class DisplayPollCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DisplayPollCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DisplayPollCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE), pe);
        }
    }
}
