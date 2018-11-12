package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ResearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ResearchCommand object
 */
public class ResearchCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ResearchCommand
     * and returns an ResearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ResearchCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ResearchCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ResearchCommand.MESSAGE_USAGE), pe);
        }
    }
}
