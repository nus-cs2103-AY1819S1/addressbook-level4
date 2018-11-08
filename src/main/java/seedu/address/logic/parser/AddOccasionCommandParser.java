package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONLOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OCCASIONNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddOccasionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.occasion.Occasion;
import seedu.address.model.occasion.OccasionDate;
import seedu.address.model.occasion.OccasionDescriptor;
import seedu.address.model.occasion.OccasionLocation;
import seedu.address.model.occasion.OccasionName;
import seedu.address.model.tag.Tag;


/**
 * AddOccasionCommandParser
 */
public class AddOccasionCommandParser implements Parser<AddOccasionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddOccasionCommand
     * and returns a new Occasion object of the given parameters.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOccasionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OCCASIONNAME, PREFIX_OCCASIONDATE, PREFIX_OCCASIONLOCATION,
                        PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_OCCASIONNAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddOccasionCommand.MESSAGE_USAGE));
        }
        OccasionDescriptor addOccasionDescriptor = new OccasionDescriptor();

        OccasionName occasionName = ParserUtil.parseOccasionName(argMultimap.getValue(PREFIX_OCCASIONNAME).get());
        addOccasionDescriptor.setOccasionName(occasionName);
        if (argMultimap.getValue(PREFIX_OCCASIONDATE).isPresent()) {
            OccasionDate occasionDate = ParserUtil.parseOccasionDate(argMultimap.getValue(PREFIX_OCCASIONDATE).get());
            addOccasionDescriptor.setOccasionDate(occasionDate);
        }
        if (argMultimap.getValue(PREFIX_OCCASIONLOCATION).isPresent()) {
            OccasionLocation location = ParserUtil.parseOccasionLocation(argMultimap
                    .getValue(PREFIX_OCCASIONLOCATION).get());
            addOccasionDescriptor.setOccasionLocation(location);
        }
        parseTagsForAdd(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(addOccasionDescriptor::setTags);

        Occasion occasion = new Occasion(addOccasionDescriptor);
        return new AddOccasionCommand(occasion);
    }


    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForAdd(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }
}
