package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BEFORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.calendarevent.DatePredicate;
import seedu.address.model.calendarevent.DateTime;
import seedu.address.model.calendarevent.FuzzySearchComparator;
import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.model.calendarevent.TagsPredicate;

/**
 * Parses input arguments and creates a new FindEventCommand object
 */
public class FindCommandParser implements Parser<FindEventCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindEventCommand
     * and returns an FindEventCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindEventCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FROM, PREFIX_BEFORE, PREFIX_TAG);

        // Parses date/time from and date/time before (if any)
        DateTime dateFrom = null;
        if (argMultimap.getValue(PREFIX_FROM).isPresent()) {
            String dateFromString = argMultimap.getValue(PREFIX_FROM).get();
            dateFrom = ParserUtil.parseDateTime(dateFromString);
        }
        DateTime dateBefore = null;
        if (argMultimap.getValue(PREFIX_BEFORE).isPresent()) {
            String dateToString = argMultimap.getValue(PREFIX_BEFORE).get();
            dateBefore = ParserUtil.parseDateTime(dateToString);
        }
        if (dateBefore != null && dateFrom != null) {
            // Date/time from must be chronologically earlier than date/time before
            if (dateFrom.isAfter(dateBefore)) {
                throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DatePredicate.MESSAGE_DATE_PREDICATE_CONSTRAINTS));
            }
        }

        List<String> tagList = argMultimap.getAllValues(PREFIX_TAG)
                                            .stream()
                                            .map(String::trim)
                                            .collect(Collectors.toList());

        if (tagList.stream().anyMatch(String::isEmpty)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
        }

        String keywords = argMultimap.getPreamble();
        String trimmedKeywords = keywords.trim();
        List<String> nameKeywords = Collections.emptyList();

        if (trimmedKeywords.isEmpty()) {
            // Throws exception if no keywords are provided & and there are no
            // valid 'from' date/time, 'before' date/time or tags
            if (!argMultimap.getValue(PREFIX_FROM).isPresent()
                && !argMultimap.getValue(PREFIX_BEFORE).isPresent()
                && !argMultimap.getValue(PREFIX_TAG).isPresent()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
            }
        } else {
            // Collects trimmed list of keywords
            nameKeywords = Arrays.stream(trimmedKeywords.split("\\s+"))
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toList());
        }

        return new FindEventCommand(new FuzzySearchFilterPredicate(nameKeywords),
            new FuzzySearchComparator(nameKeywords),
            new DatePredicate(dateFrom, dateBefore),
            new TagsPredicate(tagList));
    }
}
