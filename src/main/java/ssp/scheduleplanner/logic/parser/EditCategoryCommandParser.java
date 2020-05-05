package ssp.scheduleplanner.logic.parser;

import java.util.stream.Stream;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.EditCategoryCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCategoryCommand object
 */
public class EditCategoryCommandParser implements Parser<EditCategoryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCategoryCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCategoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CATEGORY);
        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditCategoryCommand.MESSAGE_USAGE));
        }
        String[] oldAndNewNames = ParserUtil.parseCategoryNames(argMultimap.getAllValues(CliSyntax.PREFIX_CATEGORY));

        return new EditCategoryCommand(oldAndNewNames[0], oldAndNewNames[1]);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
