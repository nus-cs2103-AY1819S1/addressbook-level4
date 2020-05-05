package ssp.scheduleplanner.logic.parser;

import java.util.stream.Stream;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.RemoveCategoryCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new RemoveCategoryCommand object
 */
public class RemoveCategoryCommandParser implements Parser<RemoveCategoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the RemoveCategoryCommand
     * and returns an RemoveCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemoveCategoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CATEGORY);
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    RemoveCategoryCommand.MESSAGE_USAGE));
        }

        String categoryName = ParserUtil.parseCategoryName(argMultimap.getValue(CliSyntax.PREFIX_CATEGORY).get());

        return new RemoveCategoryCommand(categoryName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
