package seedu.address.logic.parser.jio;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.jio.DeleteJioCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserRestaurantUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.restaurant.Name;

/**
 * Parses input arguments and creates a new DeleteJioCommand object
 */
public class DeleteJioCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddJioCommand
     * and returns an AddJioCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteJioCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteJioCommand.MESSAGE_USAGE));
        }

        Name name = ParserRestaurantUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());

        return new DeleteJioCommand(name);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
