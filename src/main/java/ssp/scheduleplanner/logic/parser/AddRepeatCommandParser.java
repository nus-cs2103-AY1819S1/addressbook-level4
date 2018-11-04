package ssp.scheduleplanner.logic.parser;

import java.util.Set;
import java.util.stream.Stream;

import ssp.scheduleplanner.commons.core.Messages;
import ssp.scheduleplanner.logic.commands.AddRepeatCommand;
import ssp.scheduleplanner.logic.parser.exceptions.ParseException;
import ssp.scheduleplanner.model.tag.Tag;
import ssp.scheduleplanner.model.task.Date;
import ssp.scheduleplanner.model.task.Interval;
import ssp.scheduleplanner.model.task.Name;
import ssp.scheduleplanner.model.task.Priority;
import ssp.scheduleplanner.model.task.Repeat;
import ssp.scheduleplanner.model.task.Task;
import ssp.scheduleplanner.model.task.Venue;

/**
 *  Parses input arguments and creates a new AddRepeatCommand object
 */
public class AddRepeatCommandParser implements Parser<AddRepeatCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRepeatCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_REPEAT, CliSyntax.PREFIX_INTERVAL,
                        CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_PRIORITY,
                        CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_REPEAT, CliSyntax.PREFIX_INTERVAL,
                CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_VENUE, CliSyntax.PREFIX_DATE, CliSyntax.PREFIX_PRIORITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddRepeatCommand.MESSAGE_USAGE));
        }

        Interval interval = ParserUtil.parseInterval(argMultimap.getValue(CliSyntax.PREFIX_INTERVAL).get());
        Repeat repeat = ParserUtil.parseRepeat(argMultimap.getValue(CliSyntax.PREFIX_REPEAT).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(CliSyntax.PREFIX_NAME).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(CliSyntax.PREFIX_DATE).get());
        Priority priority = ParserUtil.parsePriority(argMultimap.getValue(CliSyntax.PREFIX_PRIORITY).get());
        Venue venue = ParserUtil.parseVenue(argMultimap.getValue(CliSyntax.PREFIX_VENUE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));

        Task task = new Task(name, date, priority, venue, tagList);

        return new AddRepeatCommand(task, repeat, interval);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
