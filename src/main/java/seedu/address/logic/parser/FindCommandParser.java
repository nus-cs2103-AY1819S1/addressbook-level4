package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREAMBLE_EXACT_MATCH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wish.WishContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected FORMAT
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (!argsContainsPrefixes(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG, PREFIX_REMARK);


        List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
        List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
        List<String> remarkKeywords = argMultimap.getAllValues(PREFIX_REMARK);

        String preamble = argMultimap.getPreamble();

        boolean isExactMatch = preamble.equals(PREAMBLE_EXACT_MATCH);

        return new FindCommand(new WishContainsKeywordsPredicate(nameKeywords, tagKeywords,
                remarkKeywords, isExactMatch));
    }

    private boolean argsContainsPrefixes(String args) {
        return args.contains(PREFIX_NAME.toString())
                || args.contains(PREFIX_TAG.toString())
                || args.contains(PREFIX_REMARK.toString());
    }

}
