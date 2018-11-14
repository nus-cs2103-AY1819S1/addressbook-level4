package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignmentStatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AssignmentStatsCommand object
 */
public class AssignmentStatsCommandParser implements Parser<AssignmentStatsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AssignmentStatsCommand
     * and returns an AssignmentStatsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AssignmentStatsCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new AssignmentStatsCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignmentStatsCommand.MESSAGE_USAGE), pe);
        }
    }

}
