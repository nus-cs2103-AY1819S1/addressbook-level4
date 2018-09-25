package seedu.address.logic.parser;

//@@author GilgameshTC

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.stream.Stream;

import seedu.address.logic.commands.CreateCalendarCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new CreateCalendarCommand object.
 */
public class CreateCalendarCommandParser implements Parser<CreateCalendarCommand>{

    /**
     * Parses the given {@code String} of arguments in the context of the CreateCalendarCommand
     * and returns an CreateCalendarCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public CreateCalendarCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MONTH, PREFIX_YEAR);

        if (!arePrefixesPresent(argMultimap, PREFIX_MONTH, PREFIX_YEAR)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCalendarCommand.MESSAGE_USAGE));
        }

        String month = argMultimap.getValue(PREFIX_MONTH).get();
        String year = argMultimap.getValue(PREFIX_YEAR).get();

        return new CreateCalendarCommand(month, year);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
