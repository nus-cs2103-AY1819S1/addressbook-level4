package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ADDRESS_FULL;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG_FULL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.Address;
import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.model.tag.Tag;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_ADDRESS_FULL,
                PREFIX_TAG, PREFIX_TAG_FULL);

        Optional<Address> address = parseAndGetAddress(argMultimap);

        Optional<Set<Tag>> tags = parseAndGetTags(argMultimap);

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new RideContainsKeywordsPredicate(Arrays.asList(nameKeywords),
                address, tags));
    }

    /**
     * Checks if the argument multimap contains the "tag" or "t/" prefix and returns a set of tags
     * if present.
     */
    private Optional<Set<Tag>> parseAndGetTags(ArgumentMultimap argMultimap) throws ParseException {
        Set<Tag> tagSet = new HashSet<>();
        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            tagSet.addAll(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));
        }
        if (argMultimap.getValue(PREFIX_TAG_FULL).isPresent()) {
            tagSet.addAll(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG_FULL)));
        }
        if (tagSet.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(tagSet);
    }

    /**
     * Checks if the argument multimap contains the "thanepark" or "a/" prefix and returns an Address
     * object if either are present.
     */
    private Optional<Address> parseAndGetAddress(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            return Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        } else if (argMultimap.getValue(PREFIX_ADDRESS_FULL).isPresent()) {
            return Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS_FULL).get()));
        }
        return Optional.empty();
    }
}
