package seedu.address.logic.parser.eventparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalTime;

import seedu.address.logic.commands.eventcommands.SetTimeCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses inputs arguments and creates a new SetTimeCommand object.
 */
public class SetTimeCommandParser implements Parser<SetTimeCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetDateCommand
     * and returns an SetDateCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetTimeCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        if (!argMultimap.getValue(PREFIX_TIME).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetTimeCommand.MESSAGE_USAGE));
        }

        LocalTime time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());

        return new SetTimeCommand(time);
    }
}
