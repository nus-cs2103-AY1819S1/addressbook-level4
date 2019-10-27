package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBHEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new addEntryCommand object
 */
public class AddEntryCommandParser implements Parser<AddEntryCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddEntryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_TITLE, PREFIX_SUBHEADER,
                PREFIX_CATEGORY, PREFIX_TAG, PREFIX_DURATION);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));
        }

        ResumeEntry entry;
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());
        Set<Tag> tags = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        if (arePrefixesAbsent(argMultimap, PREFIX_TITLE, PREFIX_SUBHEADER, PREFIX_DURATION)) {
            entry = new ResumeEntry(category, new EntryInfo(), tags);
            return new AddEntryCommand(entry);
        }

        if (arePrefixesPresent(argMultimap, PREFIX_TITLE, PREFIX_SUBHEADER, PREFIX_DURATION)) {
            String header = ParserUtil.parseString(argMultimap.getValue(PREFIX_TITLE).get());
            String subHeader = ParserUtil.parseString(argMultimap.getValue(PREFIX_SUBHEADER).get());
            String duration = ParserUtil.parseString(argMultimap.getValue(PREFIX_DURATION).get());
            EntryInfo info = ParserUtil.parseEntryInfo(header, subHeader, duration);

            entry = new ResumeEntry(category, info, tags);
            return new AddEntryCommand(entry);
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEntryCommand.MESSAGE_USAGE));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if all of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesAbsent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).noneMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
