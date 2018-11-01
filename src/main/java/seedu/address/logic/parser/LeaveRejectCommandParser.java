package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LeaveRejectCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LeaveRejectCommand object
 */
public class LeaveRejectCommandParser implements Parser<LeaveRejectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LeaveRejectCommand
     * and returns an LeaveRejectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LeaveRejectCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LeaveRejectCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveRejectCommand.MESSAGE_USAGE), pe);
        }
    }

}
