package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.LeaveApproveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new LeaveApproveCommand object
 */
public class LeaveApproveCommandParser implements Parser<LeaveApproveCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the LeaveApproveCommand
     * and returns an LeaveApproveCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public LeaveApproveCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new LeaveApproveCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, LeaveApproveCommand.MESSAGE_USAGE), pe);
        }
    }

}
