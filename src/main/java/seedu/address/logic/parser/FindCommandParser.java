package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ride.Address;
import seedu.address.model.ride.RideContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMutlimap = ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_TAG);
        Optional<Address> address = !argMutlimap.getValue(PREFIX_ADDRESS).isPresent()
                                    ? Optional.empty()
                                    : Optional.of(ParserUtil.parseAddress(argMutlimap.getValue(PREFIX_ADDRESS).get()));
        Optional<Set<Tag>> tags = argMutlimap.getValue(PREFIX_TAG).isPresent()
                                  ? Optional.of(ParserUtil.parseTags(argMutlimap.getAllValues(PREFIX_TAG)))
                                  : Optional.empty();

        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new RideContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                address, tags));
    }
}
