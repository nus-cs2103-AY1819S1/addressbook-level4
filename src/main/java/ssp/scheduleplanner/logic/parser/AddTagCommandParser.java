package ssp.scheduleplanner.logic.parser;

import java.util.stream.Stream;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.AddTagCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;
import ssp.scheduleplanner.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddTagCommand object
 */
public class AddTagCommandParser implements Parser<AddTagCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddTagCommand
     * and returns an AddTagCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_CATEGORY, CliSyntax.PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_CATEGORY, CliSyntax.PREFIX_TAG)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTagCommand.MESSAGE_USAGE));
        }

        String categoryName = ParserUtil.parseCategoryName(argMultimap.getValue(CliSyntax.PREFIX_CATEGORY).get());
        Tag tag = ParserUtil.parseTag(argMultimap.getValue(CliSyntax.PREFIX_TAG).get());

        return new AddTagCommand(tag, categoryName);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
