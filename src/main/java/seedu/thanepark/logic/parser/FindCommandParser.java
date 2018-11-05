package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG_FULL;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE_FULL;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.thanepark.logic.commands.FindCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.RideContainsKeywordsPredicate;
import seedu.thanepark.model.ride.Zone;
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

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ZONE, PREFIX_ZONE_FULL,
                PREFIX_TAG, PREFIX_TAG_FULL);

        Optional<Zone> address = parseAndGetZone(argMultimap);

        Optional<Set<Tag>> tags = parseAndGetTags(argMultimap);

        String[] nameKeywords = trimmedArgs.split("\\s+");

        nameKeywords = removeOtherArguments(nameKeywords);

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
     * Checks if the argument multimap contains the "thanepark" or "a/" prefix and returns an Zone
     * object if either are present.
     */
    private Optional<Zone> parseAndGetZone(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_ZONE).isPresent()) {
            return Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ZONE).get()));
        } else if (argMultimap.getValue(PREFIX_ZONE_FULL).isPresent()) {
            return Optional.of(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ZONE_FULL).get()));
        }
        return Optional.empty();
    }

    private String[] removeOtherArguments(String[] keywords) {
        List<String> trimmedKeyWords = Arrays.asList(keywords);
        int[] indexes = new int[4];
        indexes[0] = trimmedKeyWords.indexOf(PREFIX_TAG.getPrefix());
        indexes[1] = trimmedKeyWords.indexOf(PREFIX_TAG_FULL.getPrefix());
        indexes[2] = trimmedKeyWords.indexOf(PREFIX_ZONE.getPrefix());
        indexes[3] = trimmedKeyWords.indexOf(PREFIX_ZONE_FULL.getPrefix());
        int smallestPositiveIndex = -1;
        for (int i : indexes) {
            if (i >= 0 && (smallestPositiveIndex == -1 || smallestPositiveIndex > i)) {
                smallestPositiveIndex = i;
            }
        }
        if (smallestPositiveIndex == -1) {
            return keywords;
        }
        int lastIndexOfNameString = smallestPositiveIndex;
        String[] trimmedArgs = new String[lastIndexOfNameString];
        for (int i = 0 ; i < lastIndexOfNameString; i++) {
            trimmedArgs[i] = trimmedKeyWords.get(i);
        }

        return trimmedArgs;
    }
}
