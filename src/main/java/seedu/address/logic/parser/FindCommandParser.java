package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS_FULL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG_FULL;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_ADDRESS_FULL,
                PREFIX_TAG, PREFIX_TAG_FULL);

        Optional<Address> address;
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            address = Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        } else if (argMultimap.getValue(PREFIX_ADDRESS_FULL).isPresent()) {
            address = Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS_FULL).get()));
        } else {
            address = Optional.empty();
        }

        Optional<Set<Tag>> tags = argMultimap.getValue(PREFIX_TAG).isPresent()
                                  ? Optional.of(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)))
                                  : Optional.empty();

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new RideContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                address, tags));
    }
}
