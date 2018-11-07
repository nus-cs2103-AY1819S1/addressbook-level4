package seedu.jxmusic.logic.parser;

import static seedu.jxmusic.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.stream.Stream;

import javafx.util.Duration;

import seedu.jxmusic.logic.commands.SeekCommand;
import seedu.jxmusic.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SeekCommand object
 */
public class SeekCommandParser implements Parser<SeekCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SeekCommand
     * and returns an SeekCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SeekCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_TIME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SeekCommand.MESSAGE_USAGE));
        }
        String seekTimeString = argMultimap.getValue(PREFIX_TIME).get();
        Duration seekTime = null;
        if (!seekTimeString.isEmpty()) {
            double time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
            seekTime = new Duration(time);
        }
        return new SeekCommand(seekTime);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}

