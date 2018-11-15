package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ReferralLetterCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ReferralLetterCommand object
 */
public class ReferralLetterCommandParser implements Parser<ReferralLetterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReferralLetterCommand
     * and returns an ReferralLetterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReferralLetterCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new ReferralLetterCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReferralLetterCommand.MESSAGE_USAGE), pe);
        }
    }
}
