package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GenerateLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventDate;

/**
 * Parses input arguments and creates a new GenerateLocationCommand object.
 */
public class GenerateLocationCommandParser implements Parser<GenerateLocationCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GenerateLocationCommand
     * and returns an GenerateLocationCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GenerateLocationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    GenerateLocationCommand.MESSAGE_USAGE));
        }

        EventDate eventDate = ParserUtil.parseEventDate(argMultimap.getValue(PREFIX_DATE).get());
        Index eventIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

        return new GenerateLocationCommand(eventDate, eventIndex);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
