package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.GendataCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and returns a new GendataCommand.
 */
public class GendataCommandParser implements Parser<GendataCommand> {

    @Override
    public GendataCommand parse(String args) throws ParseException {
        try {
            int numPersons = Integer.parseInt(args.trim());
            return new GendataCommand(numPersons);
        } catch (NumberFormatException e) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, GendataCommand.MESSAGE_USAGE), e);
        }
    }
}
