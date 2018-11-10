package ssp.scheduleplanner.logic.parser;

import java.util.stream.Stream;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.ShowTagsCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ShowTagsCommand object
 */
public class ShowTagsCommandParser implements Parser<ShowTagsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ShowTagsCommand
     * and returns a ShowTagsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowTagsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CATEGORY);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowTagsCommand.MESSAGE_USAGE));
        }

        String categoryName = ParserUtil.parseCategoryName(argMultimap.getValue(CliSyntax.PREFIX_CATEGORY).get());

        return new ShowTagsCommand(categoryName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
