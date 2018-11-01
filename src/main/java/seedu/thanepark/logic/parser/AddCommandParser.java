package seedu.thanepark.logic.parser;

import static seedu.thanepark.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_MAINTENANCE;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_WAITING_TIME;
import static seedu.thanepark.logic.parser.CliSyntax.PREFIX_ZONE;

import java.util.Set;
import java.util.stream.Stream;

import seedu.thanepark.logic.commands.AddCommand;
import seedu.thanepark.logic.parser.exceptions.ParseException;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME,
                        PREFIX_MAINTENANCE, PREFIX_WAITING_TIME, PREFIX_ZONE, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ZONE, PREFIX_MAINTENANCE, PREFIX_WAITING_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Maintenance maintenance = ParserUtil.parseMaintenance(argMultimap.getValue(PREFIX_MAINTENANCE).get());
        WaitTime waitTime = ParserUtil.parseWaitingTime(argMultimap.getValue(PREFIX_WAITING_TIME).get());
        Zone zone = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ZONE).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Ride ride = new Ride(name, maintenance, waitTime, zone, tagList);

        return new AddCommand(ride);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
