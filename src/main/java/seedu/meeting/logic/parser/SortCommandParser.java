package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.meeting.logic.commands.SortCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SortCommand object
 */
public class SortCommandParser implements Parser<SortCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SortCommand
     * and returns an SortCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SortCommand parse(String args) throws ParseException {
        try {
            return new SortCommand(ParserUtil.parsePersonPropertyComparator(args));
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE), pe);
        }
    }
}
