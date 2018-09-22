package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Date;

import seedu.address.logic.commands.eventcommands.SetDateCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses inputs arguments and creates a new SetDateCommand object.
 */
public class SetDateCommandParser implements Parser<SetDateCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetDateCommand
     * and returns an SetDateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetDateCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE);

        if (!argMultimap.getValue(PREFIX_DATE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetDateCommand.MESSAGE_USAGE));
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        return new SetDateCommand(date);
    }
}
