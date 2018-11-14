package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONNAME;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindOccasionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.occasion.OccasionDateContainsKeywordsPredicate;
import seedu.address.model.occasion.OccasionLocationContainsKeywordsPredicate;
import seedu.address.model.occasion.OccasionNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindOccasionCommand object
 */
public class FindOccasionCommandParser implements Parser<FindOccasionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindOccasionCommand
     * and returns a new FindOccasionCommand object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindOccasionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OCCASIONNAME, PREFIX_OCCASIONDATE, PREFIX_OCCASIONLOCATION);
        if (!anyPrefixesPresent(argMultimap, PREFIX_OCCASIONNAME, PREFIX_OCCASIONDATE, PREFIX_OCCASIONLOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindOccasionCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValue(PREFIX_OCCASIONNAME).isPresent()) {
            String[] occasionNameKeyWords = argMultimap.getValue(PREFIX_OCCASIONNAME).get().trim().split("\\s+");
            return new FindOccasionCommand(
                    new OccasionNameContainsKeywordsPredicate(Arrays.asList(occasionNameKeyWords)));
        } else if (argMultimap.getValue(PREFIX_OCCASIONDATE).isPresent()) {
            String[] occasionDateKeyWords = argMultimap.getValue(PREFIX_OCCASIONDATE).get().trim().split("\\s+");
            return new FindOccasionCommand(
                    new OccasionDateContainsKeywordsPredicate(Arrays.asList(occasionDateKeyWords)));
        } else {
            String[] occasionLocationKeyWords =
                    argMultimap.getValue(PREFIX_OCCASIONLOCATION).get().trim().split("\\s+");
            return new FindOccasionCommand(
                    new OccasionLocationContainsKeywordsPredicate(Arrays.asList(occasionLocationKeyWords)));
        }

    }

    /**
     * Returns true if at least one of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }


}
